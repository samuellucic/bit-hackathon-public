'use client';

import { createContext, ReactNode, useCallback, useEffect, useState } from 'react';
import { getItem, removeItem } from '../api/local-storage';
import { Authority } from '../types/types';
import { authorityKey, tokenKey } from '../lib/constants';

export type UserContextType = {
  isLoggedIn: boolean;
  setIsLoggedIn: (loggedIn: boolean) => void;
  authority: Authority | null;
  setAuthority: (authority: Authority) => void;
  logout: () => void;
};

export const UserContext = createContext<UserContextType>({
  isLoggedIn: false,
  setIsLoggedIn: () => {},
  authority: null,
  setAuthority: () => {},
  logout: () => {},
});

type Props = {
  children: ReactNode;
};

export const UserContextProvider = ({ children }: Props) => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
  const [authority, setAuthority] = useState<Authority | null>(null);

  const logout = useCallback(() => {
    setAuthority(null);
    setIsLoggedIn(false);
    removeItem(tokenKey);
    removeItem(authorityKey);
  }, []);

  useEffect(() => {
    setIsLoggedIn(!!getItem(tokenKey));
    setAuthority(getItem(authorityKey)! as Authority);
  }, []);

  return (
    <UserContext.Provider
      value={{
        isLoggedIn,
        setIsLoggedIn,
        authority,
        setAuthority,
        logout,
      }}>
      {children}
    </UserContext.Provider>
  );
};
