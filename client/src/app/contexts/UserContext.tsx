'use client';

import { createContext, ReactNode, useState } from 'react';
import { getItem } from '../api/local-storage';

export type UserContextType = {
  isLoggedIn: boolean;
  setIsLoggedIn: (loggedIn: boolean) => void;
};

export const UserContext = createContext<UserContextType>({
  isLoggedIn: false,
  setIsLoggedIn: () => {},
});

type Props = {
  children: ReactNode;
};

export const UserContextProvider = ({ children }: Props) => {
  const [isLoggedIn, setIsLoggedIn] = useState(!!getItem('auth-id-token'));

  return (
    <UserContext.Provider
      value={{
        isLoggedIn,
        setIsLoggedIn,
      }}>
      {children}
    </UserContext.Provider>
  );
};
