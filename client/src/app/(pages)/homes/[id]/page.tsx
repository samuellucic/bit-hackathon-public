'use client';

import React, { useEffect, useState } from 'react';
import { Container, Typography } from '@mui/material';
import ReservationCalendar from '@/app/components/Reservation/ReservationsCalendar';
import ReserveDateTimePickers from '@/app/components/Reservation/ReserveDateTimePickers';
import { useParams } from 'next/navigation';
import styles from './page.module.css';
import { getOccupation, ReservationTimeRange } from '@/app/api/api';
import { Reservation } from '@/app/components/Reservation/helper';

const CommunityHome = () => {
  const [reservations, setReservations] = useState<Reservation[]>([]);

  const { id } = useParams();
  const usableId = Array.isArray(id) ? id[0] : id;

  useEffect(() => {
    const fetchHomes = async () => {
      try {
        const from = new Date();
        const to = new Date(from);
        to.setMonth(from.getMonth() + 6);
        const data = await getOccupation(Number(usableId), from, to);
        const reservations = data.map((range: ReservationTimeRange) => ({
          title: 'Rezervirano',
          reservationStart: new Date(range.start),
          reservationEnd: new Date(range.end),
        }));
        setReservations(reservations);
      } catch (error) {
        console.error(`Error fetching occupation for community home with id ${usableId} `, error);
      }
    };
    fetchHomes();
  }, []);

  return (
    <Container className={styles.container}>
      <Typography variant="h4" sx={{ marginBottom: '2rem' }}>
        Detalji društvenog doma
      </Typography>
      <ReserveDateTimePickers id={usableId} />
      <ReservationCalendar reservations={reservations} />
    </Container>
  );
};

export default CommunityHome;
