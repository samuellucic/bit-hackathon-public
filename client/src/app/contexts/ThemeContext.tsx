'use client';

import { ReactNode } from 'react';
import { useColorScheme } from '@mui/material';

type Props = {
  children: ReactNode;
};

export const ThemeWrapper = ({ children }: Props) => {
  const { mode } = useColorScheme();

  if (!mode) {
    return null;
  }
  return <>{children}</>;
};
