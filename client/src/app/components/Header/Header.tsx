'use client';

import React, { useCallback, useContext, useState } from 'react';
import { AppBar, Button, IconButton, Menu, MenuItem, Toolbar, Typography } from '@mui/material';
import Link from 'next/link';
import { Brightness4, Brightness7 } from '@mui/icons-material';
import styles from './Header.module.css';
import { ThemeType } from '../../types/types';
import { UserContext } from '../../contexts/UserContext';
import { useRouter } from 'next/navigation';

type SubmenuItem = {
  url: string;
  label: string;
};

type HeaderItem = {
  url: string;
  label: string;
  submenu?: SubmenuItem[];
};

const headerItems: HeaderItem[] = [
  { url: '/home', label: 'Početna' },
  {
    url: '/about',
    label: 'O nama',
    submenu: [
      { url: 'https://www.linkedin.com/in/adrian-babic/', label: 'Adrian' },
      { url: 'https://www.linkedin.com/in/filip-pankretic/', label: 'Filip' },
      { url: 'https://www.linkedin.com/in/matej-istuk-978b34278/', label: 'Matej' },
      { url: 'https://www.linkedin.com/in/samuel-luci%C4%87-812b38237/', label: 'Samuel' },
      { url: 'https://www.linkedin.com/in/vatroslav-jakopec-a83b3229b/', label: 'Vatroslav' },
    ],
  },
  { url: 'https://bithack.tpbj.hr/', label: 'B:IT Hack' },
];

const loginItem: HeaderItem = { url: '/login', label: 'Prijava' };

export type HeaderProps = {
  theme: ThemeType;
  onThemeChange: (theme: ThemeType) => void;
};

const Header = ({ theme, onThemeChange }: HeaderProps) => {
  const router = useRouter();
  const { isLoggedIn, logout } = useContext(UserContext);

  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [currentSubmenu, setCurrentSubmenu] = useState<string | null>(null);
  const [isDarkMode, setIsDarkMode] = useState(theme === 'dark'); // Track theme mode

  const handleClick = (event: React.MouseEvent<HTMLElement>, label: string) => {
    setAnchorEl(event.currentTarget);
    setCurrentSubmenu(label);
  };

  const handleClose = () => {
    setAnchorEl(null);
    setCurrentSubmenu(null);
  };

  const toggleTheme = useCallback(() => {
    setIsDarkMode((isDarkMode) => {
      onThemeChange(!isDarkMode ? 'light' : 'dark');
      return !isDarkMode;
    });
  }, [onThemeChange]);

  const logoutUser = useCallback(() => {
    logout();
    router.push('/home');
  }, [router, logout]);

  return (
    <AppBar position={'sticky'} className={styles.appBar}>
      <Toolbar>
        <Typography variant="h6" className={styles.title}>
          Društveni domovi Bjelovar
        </Typography>

        <IconButton onClick={toggleTheme} color="inherit" sx={{ marginLeft: 'auto' }}>
          {isDarkMode ? <Brightness7 /> : <Brightness4 />}
        </IconButton>

        {headerItems.map(({ label, url, submenu }) => (
          <div key={url}>
            {submenu ? (
              <>
                <Button
                  sx={{
                    color: 'white',
                  }}
                  aria-controls={currentSubmenu === label ? 'simple-menu' : undefined}
                  aria-haspopup="true"
                  onClick={(e) => handleClick(e, label)}
                  className={styles.menuButton}>
                  {label}
                </Button>
                <Menu id="simple-menu" anchorEl={anchorEl} open={currentSubmenu === label} onClose={handleClose}>
                  {submenu.map(({ label, url }) => (
                    <MenuItem key={url} onClick={handleClose}>
                      <Link href={url} className={styles.submenuLink}>
                        {label}
                      </Link>
                    </MenuItem>
                  ))}
                </Menu>
              </>
            ) : (
              <Link href={url} className={styles.link}>
                <Button
                  sx={{
                    color: 'white',
                  }}
                  className={styles.menuButton}>
                  {label}
                </Button>
              </Link>
            )}
          </div>
        ))}
        {isLoggedIn ? (
          <Button
            sx={{
              color: 'white',
            }}
            className={styles.menuButton}
            onClick={logoutUser}>
            Odjava
          </Button>
        ) : (
          <Link href={loginItem.url} className={styles.link}>
            <Button
              sx={{
                color: 'white',
              }}
              className={styles.menuButton}>
              {loginItem.label}
            </Button>
          </Link>
        )}
      </Toolbar>
    </AppBar>
  );
};

export default Header;
