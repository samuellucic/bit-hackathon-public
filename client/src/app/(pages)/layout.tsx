'use client';

import React, { ReactNode, useCallback, useState } from 'react';
import { ThemeType } from '../types/types';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { darkTheme, lightTheme } from '@/theme/theme';
import Header from '../components/Header/Header';
import styles from './page.module.css';

const Layout = ({ children }: { children: ReactNode }) => {
  const [theme, setTheme] = useState<ThemeType>('light');

  const handleThemeChange = useCallback((theme: ThemeType) => {
    setTheme(theme);
  }, []);

  return (
    <ThemeProvider theme={theme === 'dark' ? darkTheme : lightTheme}>
      <CssBaseline />
      <Header theme={theme} onThemeChange={handleThemeChange} />
      <main className={styles.main}>{children}</main>
    </ThemeProvider>
  );
};

export default Layout;
