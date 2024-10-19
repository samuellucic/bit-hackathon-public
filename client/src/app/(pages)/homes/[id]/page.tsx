'use client';

import React from 'react';
import { Stack, Typography } from '@mui/material';
import ReservationCalendar from '@/app/components/Reservation/ReservationsCalendar';
import { Reservation } from '@/app/components/Reservation/helper';
import ReserveDateTimePickers from '@/app/components/Reservation/ReserveDateTimePickers';
import { useParams } from 'next/navigation';

const reservations: Reservation[] = [
  {
    title: 'Rezervirano',
    reservationStart: new Date('2024-10-21T14:00:00'),
    reservationEnd: new Date('2024-10-21T16:00:00'),
  },
  {
    title: 'Rezervirano',
    reservationStart: new Date('2024-10-25T09:00:00'),
    reservationEnd: new Date('2024-10-25T12:00:00'),
  },
  {
    title: 'Rezervirano',
    reservationStart: new Date('2024-10-25T11:00:00'),
    reservationEnd: new Date('2024-10-25T19:00:00'),
  },
  {
    title: 'Rezervirano',
    reservationStart: new Date('2024-10-30T18:00:00'),
    reservationEnd: new Date('2024-10-30T20:00:00'),
  },
];

const CommunityHome = () => {
  const { id } = useParams();

  return (
    <Stack spacing={4} sx={{ padding: '20px' }}>
      <Typography variant="h4">Detalji društvenog doma</Typography>
      <ReserveDateTimePickers id={Array.isArray(id) ? id[0] : id} />
      <ReservationCalendar reservations={reservations} />
    </Stack>
  );
};

export default CommunityHome;