import React from 'react';
import styles from './Header.module.css';
import Link from 'next/link';

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
  { url: '/bithack', label: 'B:IThack' },
];

const Header: React.FC = () => {
  return (
    <header className={styles.header}>
      <h1>Welcome to Our Hackathon Project</h1>
      <nav>
        <ul className={styles.navList}>
          {headerItems.map(({ label, url, submenu }) => (
            <li key={url} className={styles.navItem}>
              <Link href={url} className={styles.navLink}>
                {label}
              </Link>
              {submenu && (
                <ul className={styles.submenu}>
                  {submenu.map(({ label, url }) => (
                    <li key={url}>
                      <Link href={url} className={styles.submenuLink}>
                        {label}
                      </Link>
                    </li>
                  ))}
                </ul>
              )}
            </li>
          ))}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
