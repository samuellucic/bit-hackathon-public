'use client';

import React from 'react';
import { AppBar, Button, Menu, MenuItem, Toolbar, Typography } from '@mui/material';
import Link from 'next/link';
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
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const [currentSubmenu, setCurrentSubmenu] = React.useState<string | null>(null);

  const handleClick = (event: React.MouseEvent<HTMLElement>, label: string) => {
    setAnchorEl(event.currentTarget);
    setCurrentSubmenu(label);
  };

  const handleClose = () => {
    setAnchorEl(null);
    setCurrentSubmenu(null);
  };

  return (
    <AppBar position="static" className={styles.appBar}>
      <Toolbar>
        <Typography variant="h6" className={styles.title}>
          Welcome to Our Hackathon Project
        </Typography>
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
  );
};

export default Header;
