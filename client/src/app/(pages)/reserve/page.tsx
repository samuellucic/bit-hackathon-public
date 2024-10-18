"use client";

import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { TextField, Button, Box, Container, Typography, Grid } from '@mui/material';

const mandatoryFieldMessage = "Polje je obavezno";

const schema = z.object({
    firstName: z.string().min(1, mandatoryFieldMessage),
    lastName: z.string().min(1, mandatoryFieldMessage),
    address: z.string().min(1, mandatoryFieldMessage),
    city: z.string().min(1, mandatoryFieldMessage),
    oib: z.string().min(1, mandatoryFieldMessage),
    mobilePhone: z
        .string()
        .regex(/^\d+$/, 'Broj mobitela mora sadržavati samo brojeve'),
    bank: z.string().min(1, mandatoryFieldMessage),
    iban: z
        .string()
        .min(15, 'IBAN mora sadržavati najmanje 15 znakova')
        .max(34, 'IBAN može sadržavati najviše 34 znakova'),
    purpose: z.string().min(1, mandatoryFieldMessage),
});

type FormData = z.infer<typeof schema>;

const FormPage: React.FC = () => {
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<FormData>({
        resolver: zodResolver(schema),
    });

    const onSubmit = (data: FormData) => {
        console.log(data);
    };

    return (
        <Container maxWidth="md">
            <Box sx={{ mt: 4 }}>
                <Typography variant="h4" gutterBottom style={{marginBottom: "40px"}}>
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

                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="OIB"
                                {...register('oib')}
                                error={!!errors.oib}
                                helperText={errors.oib?.message}
                            />
                        </Grid>

                        <Grid item xs={12}>
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
                                label="Opišite svrhu korištenja doma"
                                multiline
                                rows={4}
                                {...register('purpose')}
                                error={!!errors.purpose}
                                helperText={errors.purpose?.message}
                            />
                        </Grid>

                        <Grid item xs={12}>
                            <Button type="submit" variant="contained" color="primary" fullWidth>
                                Predaj zahtjev
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Box>
        </Container>
    );
};

export default FormPage;
