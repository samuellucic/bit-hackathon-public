'use client';

import React, { useCallback } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { TextField, Button, Box, Container, Typography, Grid } from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import dayjs from 'dayjs';
import 'dayjs/locale/en-gb';
import { ReserveFormData, schema } from '@/app/(pages)/homes/[id]/reserve/helper';
import styles from './page.module.css';

dayjs.locale('en-gb');

const Page = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<ReserveFormData>({
    resolver: zodResolver(schema),
  });

  const onSubmit = useCallback((data: ReserveFormData) => {
    console.log(data);
  }, []);

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

            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="Banka"
                {...register('bank')}
                error={!!errors.bank}
                helperText={errors.bank?.message}
              />
            </Grid>
            <Grid item xs={12} sm={6}>
              <TextField
                fullWidth
                label="IBAN"
                {...register('iban')}
                error={!!errors.iban}
                helperText={errors.iban?.message}
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
