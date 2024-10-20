'use client';

import React, { useCallback, useContext, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Box, Button, Checkbox, Container, FormControlLabel, Grid, TextField, Typography } from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import dayjs from 'dayjs';
import 'dayjs/locale/en-gb';
import { ReserveFormData, schema } from '@/app/(pages)/homes/[id]/reserve/helper';
import styles from './page.module.css';
import { UserContext } from '@/app/contexts/UserContext';
import { useParams, useRouter } from 'next/navigation';
import { ReservationContext } from '@/app/contexts/ReservationContext';
import { CreateReservation, UserReservation } from '@/app/types/types';
import { createReservation } from '@/app/api/api';

dayjs.locale('en-gb');

const Page = () => {
  const router = useRouter();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ReserveFormData>({
    resolver: zodResolver(schema),
  });
  const [paidByCity, setPaidByCity] = useState(false);
  const { isLoggedIn } = useContext(UserContext);
  const { timeFrom, timeTo } = useContext(ReservationContext);
  const { id } = useParams<{ id: string }>();
  const homeId: number = Number(id);

  const onSubmit = useCallback(
    async (data: ReserveFormData) => {
      let user: UserReservation | undefined = undefined;
      if (!isLoggedIn) {
        user = {
          firstName: data.firstName,
          lastName: data.lastName,
          email: data.email,
          password: data.password,
          OIB: data.oib,
          phone: data.mobilePhone,
          city: data.city,
          address: data.address,
          postalCode: '43000',
        };
      }

      const reservationRequest: CreateReservation = {
        user,
        communityHomeId: homeId,
        reason: data.purpose,
        type: paidByCity ? 'FUNERAL' : 'NORMAL',
        datetimeFrom: timeFrom.toISOString(),
        datetimeTo: timeTo.toISOString(),
      };

      if (!paidByCity) {
        reservationRequest.bank = data.bank;
        reservationRequest.iban = data.iban;
      }

      // poslati request na backend
      console.log(reservationRequest);
      await createReservation(reservationRequest);
      router.push('/login');
    },
    [homeId, isLoggedIn, paidByCity, router, timeFrom, timeTo]
  );

  const handleCheckboxChange = (event: { target: { checked: boolean | ((prevState: boolean) => boolean) } }) => {
    setPaidByCity(event.target.checked);
  };

  return (
    <Container className={styles.container}>
      <Box>
        <Typography variant="h4" gutterBottom style={{ marginBottom: '40px' }}>
          Zahtjev za rezervaciju doma
        </Typography>
        <form onSubmit={handleSubmit(onSubmit)}>
          <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Ime"
                {...register('firstName')}
                error={!!errors.firstName}
                helperText={errors.firstName?.message}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Prezime"
                {...register('lastName')}
                error={!!errors.lastName}
                helperText={errors.lastName?.message}
              />
            </Grid>
            {!isLoggedIn && (
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Email"
                  {...register('email')}
                  error={!!errors.email}
                  helperText={errors.email?.message}
                />
              </Grid>
            )}
            {!isLoggedIn && (
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Password"
                  {...register('password')}
                  error={!!errors.password}
                  helperText={errors.password?.message}
                />
              </Grid>
            )}
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Adresa"
                {...register('address')}
                error={!!errors.address}
                helperText={errors.address?.message}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Grad"
                {...register('city')}
                error={!!errors.city}
                helperText={errors.city?.message}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="OIB"
                {...register('oib')}
                error={!!errors.oib}
                helperText={errors.oib?.message}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Broj mobitela"
                {...register('mobilePhone')}
                error={!!errors.mobilePhone}
                helperText={errors.mobilePhone?.message}
              />
            </Grid>

            {!paidByCity && (
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="Banka"
                  {...register('bank')}
                  error={!!errors.bank}
                  helperText={errors.bank?.message}
                />
              </Grid>
            )}
            {!paidByCity && (
              <Grid item xs={12} sm={6}>
                <TextField
                  fullWidth
                  label="IBAN"
                  {...register('iban')}
                  error={!!errors.iban}
                  helperText={errors.iban?.message}
                />
              </Grid>
            )}
            <Grid item xs={12}>
              <FormControlLabel
                control={<Checkbox color="primary" checked={paidByCity} onChange={handleCheckboxChange} />}
                label="Financira grad"
              />
            </Grid>
            <Grid item xs={12}>
              <TextField
                fullWidth
                label="Kratko opišite svrhu korištenja doma"
                multiline
                rows={4}
                {...register('purpose')}
                error={!!errors.purpose}
                helperText={errors.purpose?.message}
              />
            </Grid>
            <Grid item xs={12}>
              <Button type="submit" variant="contained" color="primary" endIcon={<SendIcon />}>
                Predaj zahtjev
              </Button>
            </Grid>
          </Grid>
        </form>
      </Box>
    </Container>
  );
};

export default Page;
