'use client';

import React, { useState } from 'react';
import {
  AppBar,
  Button,
  createTheme,
  CssBaseline,
  IconButton,
  Menu,
  MenuItem,
  ThemeProvider,
  Toolbar,
  Typography,
} from '@mui/material';
import Link from 'next/link';
import { Brightness4, Brightness7 } from '@mui/icons-material';
import styles from './Header.module.css';

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
  { url: '/home', label: 'Home' },
  {
    url: '/about',
    label: 'About',
    submenu: [
      { url: '/about/team', label: 'Team' },
      { url: '/about/history', label: 'History' },
    ],
  },
  { url: '/contact', label: 'Contact' },
  { url: '/bithack', label: 'B:IT Hack' },
];

const Header: React.FC = () => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const [currentSubmenu, setCurrentSubmenu] = useState<string | null>(null);
  const [isDarkMode, setIsDarkMode] = useState(false); // Track theme mode

  const handleClick = (event: React.MouseEvent<HTMLElement>, label: string) => {
    setAnchorEl(event.currentTarget);
    setCurrentSubmenu(label);
  };

  const handleClose = () => {
    setAnchorEl(null);
    setCurrentSubmenu(null);
  };

  const toggleTheme = () => {
    setIsDarkMode(!isDarkMode);
  };

  const theme = createTheme({
    palette: {
      mode: isDarkMode ? 'dark' : 'light',
    },
  });

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <AppBar position="static" className={styles.appBar}>
        <Toolbar>
          <Typography variant="h6" className={styles.title}>
            Welcome to Our Hackathon Project
          </Typography>

          <IconButton onClick={toggleTheme} color="inherit" sx={{ marginLeft: 'auto' }}>
            {isDarkMode ? <Brightness7 /> : <Brightness4 />}
          </IconButton>

          {headerItems.map(({ label, url, submenu }) => (
            <div key={url}>
              {submenu ? (
                <>
                  <Button
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
                  <Button className={styles.menuButton}>{label}</Button>
                </Link>
              )}
            </div>
          ))}
        </Toolbar>
      </AppBar>
    </ThemeProvider>
  );
};

export default Header;
