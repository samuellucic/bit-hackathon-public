'use client';

import React from 'react';
import { useParams } from 'next/navigation';
import { Typography } from '@mui/material';
import ReservationCalendar from '@/app/components/Reservation/ReservationsCalendar';
import { Reservation } from '@/app/components/Reservation/typings';

const reservations: Reservation[] = [
  {
    title: 'Meeting',
    reservationStart: new Date('2024-10-21T14:00:00'),
    reservationEnd: new Date('2024-10-21T16:00:00'),
  },
  {
    title: 'Workshop',
    reservationStart: new Date('2024-10-25T09:00:00'),
    reservationEnd: new Date('2024-10-25T12:00:00'),
  },
  {
    title: 'Conference',
    reservationStart: new Date('2024-10-30T18:00:00'),
    reservationEnd: new Date('2024-10-30T20:00:00'),
  },
];

const CommunityHome = () => {
  const { id } = useParams();

  return (
    <div style={{ padding: '20px' }}>
      <Typography variant="h4">Detalji društvenog doma</Typography>
      <Typography variant="h6">{`ID doma: ${id}`}</Typography>
      <ReservationCalendar reservations={reservations} />
    </div>
  );
};

export default CommunityHome;
