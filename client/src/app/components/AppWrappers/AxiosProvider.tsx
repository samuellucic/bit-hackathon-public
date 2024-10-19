'use client';

import { ReactNode, useEffect } from 'react';
import api from '../../api/instance';
import { useRouter } from 'next/navigation';

export type AxiosProviderProps = {
  children: ReactNode;
};

const AxiosProvider = ({ children }: AxiosProviderProps) => {
  const router = useRouter();

  useEffect(() => {
    api.interceptors.response.use(
      (value) => value,
      (error) => {
        if (error.status === 401 && !error.config.baseURL.endsWith('/auth/authenticate')) {
          router.push('/login');
        }
        return Promise.reject(error);
      }
    );
  }, [router]);

  return <>{children}</>;
};

export default AxiosProvider;
