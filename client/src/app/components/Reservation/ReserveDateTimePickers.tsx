'use client';

import React, { useCallback } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { DatePicker, DateTimePicker, LocalizationProvider } from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { Button, Grid } from '@mui/material';
import { zodResolver } from '@hookform/resolvers/zod';
import { ReserveDateTimeData, schema } from '@/app/components/Reservation/helper';
import { useRouter } from 'next/navigation';

type Props = {
  onDateOfIssueChange: (date: Date) => void;
  onTimeFromChange: (date: Date) => void;
  onTimeToChange: (date: Date) => void;
  id: string;
};

const ReserveDateTimePickers = ({ id, onTimeToChange, onTimeFromChange, onDateOfIssueChange }: Props) => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<ReserveDateTimeData>({
    resolver: zodResolver(schema),
    defaultValues: {
      dateOfIssue: dayjs().toDate(),
      timeFrom: dayjs().add(1, 'hour').startOf('hour').add(8, 'day').toDate(),
      timeTo: dayjs().add(1, 'hour').startOf('hour').add(9, 'day').toDate(),
    },
  });

  const router = useRouter();

  const onSubmit = useCallback((data: ReserveDateTimeData) => {
    console.log(data);
    // add validation if selected date is available
    if (true) {
      router.push(`/homes/${id}/reserve`);
    }
  }, []);

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Grid container spacing={2}>
          <Grid item xs={12} sm={4}>
            <Controller
              name="timeFrom"
              control={control}
              render={({ field }) => (
                <DateTimePicker
                  label="Početak korištenja"
                  value={field.value ? dayjs(field.value) : null}
                  onChange={(newValue) => {
                    if (newValue) {
                      onTimeFromChange(newValue.toDate());
                    }
                    field.onChange(newValue ? newValue.toDate() : null);
                  }}
                  format="DD/MM/YYYY HH:mm"
                  slotProps={{
                    textField: {
                      fullWidth: true,
                      error: !!errors.timeFrom,
                      helperText: errors.timeFrom?.message,
                    },
                  }}
                />
              )}
            />
          </Grid>

          <Grid item xs={12} sm={4}>
            <Controller
              name="timeTo"
              control={control}
              render={({ field }) => (
                <DateTimePicker
                  label="Kraj korištenja"
                  value={field.value ? dayjs(field.value) : null}
                  onChange={(newValue) => {
                    if (newValue) {
                      onTimeToChange(newValue.toDate());
                    }
                    field.onChange(newValue ? newValue.toDate() : null);
                  }}
                  format="DD/MM/YYYY HH:mm"
                  slotProps={{
                    textField: {
                      fullWidth: true,
                      error: !!errors.timeTo,
                      helperText: errors.timeTo?.message,
                    },
                  }}
                />
              )}
            />
          </Grid>

          <Grid item xs={12} sm={4}>
            <Controller
              name="dateOfIssue"
              control={control}
              render={({ field }) => (
                <DatePicker
                  label="Datum izdavanja"
                  value={field.value ? dayjs(field.value) : null}
                  onChange={(newValue) => {
                    if (newValue) {
                      onDateOfIssueChange(newValue.toDate());
                    }
                    field.onChange(newValue ? newValue.toDate() : null);
                  }}
                  format="DD/MM/YYYY"
                  slotProps={{
                    textField: {
                      fullWidth: true,
                      error: !!errors.dateOfIssue,
                      helperText: errors.dateOfIssue?.message,
                    },
                  }}
                />
              )}
            />
          </Grid>

          <Grid item xs={6}>
            <Button type="submit" variant="contained" color="primary">
              Potvrdi
            </Button>
          </Grid>
        </Grid>
      </form>
    </LocalizationProvider>
  );
};

export default ReserveDateTimePickers;
