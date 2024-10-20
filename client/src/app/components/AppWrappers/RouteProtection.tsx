'use client';

import { ReactNode, useContext } from 'react';
import { usePathname, useRouter } from 'next/navigation';
import { UserContext } from '../../contexts/UserContext';
import { Authority } from '../../types/types';

export interface RouteProtectionProps {
  children: ReactNode;
}

type BasicUser = Exclude<Authority, 'ADMIN'>;

const AUTH_ROUTES = [/\/login/];
const NO_AUTH_ROUTES = [/\/home/, /\/homes/];
const SHARED_ROUTES: RegExp[] = [];
const INTERNAL_ROUTES: RegExp[] = [];

const AUTHORITY_ROUTES: {
  [key in BasicUser]: RegExp[];
} = {
  CUSTOMER: [...SHARED_ROUTES],
  OFFICIAL: [...SHARED_ROUTES, ...INTERNAL_ROUTES, /\/reservations-dashboard/],
  CUSTODIAN: [...SHARED_ROUTES, ...INTERNAL_ROUTES, /\/record-books-dashboard/],
  MAYOR: [...SHARED_ROUTES, ...INTERNAL_ROUTES, /\/contracts-dashboard/],
};

const HOME_URL = '/home';
const LOGIN_URL = '/login';

const routeMatches = (routes: RegExp[], path: string) => {
  return routes.some((route) => path.match(route));
};

const RouteProtection = ({ children }: RouteProtectionProps) => {
  const { isLoggedIn, authority } = useContext(UserContext);

  const path = usePathname();
  const router = useRouter();

  let redirectURL = null;

  if (!routeMatches(NO_AUTH_ROUTES, path)) {
    if (routeMatches(AUTH_ROUTES, path)) {
      if (isLoggedIn) {
        redirectURL = HOME_URL;
      }
    } else if (!isLoggedIn) {
      redirectURL = LOGIN_URL;
    } else if (
      authority &&
      authority !== 'ADMIN' &&
      !AUTHORITY_ROUTES[authority].some((route) => path.match(route)) &&
      authority !== 'CUSTOMER'
    ) {
      redirectURL = HOME_URL;
    }
  }

  if (redirectURL) {
    router.replace(redirectURL);
    return null;
  }
  return <>{children}</>;
};

export default RouteProtection;
