'use client';

import React, { ReactNode, useCallback, useState } from 'react';
import { ThemeType } from '../types/types';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { darkTheme, lightTheme } from '@/theme/theme';
import Header from '../components/Header/Header';
import styles from './page.module.css';
import { UserContextProvider } from '../contexts/UserContext';
import RouteProtection from '../components/AppWrappers/RouteProtection';
import AxiosProvider from '../components/AppWrappers/AxiosProvider';
import { ThemeWrapper } from '../contexts/ThemeContext';

const Layout = ({ children }: { children: ReactNode }) => {
  const [theme, setTheme] = useState<ThemeType>('light');

  const handleThemeChange = useCallback((theme: ThemeType) => {
    setTheme(theme);
  }, []);
 
  return (
    <ThemeProvider theme={theme === 'dark' ? darkTheme : lightTheme}>
      <ThemeWrapper>
        <CssBaseline />
        <UserContextProvider>
          <RouteProtection>
            <AxiosProvider>
              <Header theme={theme} onThemeChange={handleThemeChange} />
              <main className={styles.main}>{children}</main>
            </AxiosProvider>
          </RouteProtection>
        </UserContextProvider>
      </ThemeWrapper>
    </ThemeProvider>
  );
};

export default Layout;
