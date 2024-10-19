'use client';

import React from 'react';
import { useParams } from 'next/navigation';
import { Typography } from '@mui/material';

const CommunityHome = () => {
  const { id } = useParams();

  return (
    <div style={{ padding: '20px' }}>
      <Typography variant="h4">Detalji društvenog doma</Typography>
      <Typography variant="h6">{`ID doma: ${id}`}</Typography>
    </div>
  );
};

export default CommunityHome;
