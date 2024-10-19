import React from 'react';
import Link from 'next/link';
import { Card, CardContent, CardActions, Typography, Grid } from '@mui/material';
import styles from './page.module.css';
import { House } from '@/app/(pages)/homes/typings';

const mockHouses: House[] = [
  {
    id: 1,
    name: 'DVD Bjelovar',
    address: 'Vatroslava Velikog 62',
    postalCode: '12345',
    city: 'Bjelovar',
    area: 2500.0,
    capacity: 8,
  },
  {
    id: 2,
    name: 'Crkva Sv. Siniše',
    address: 'Nikole Tesle 321',
    postalCode: '12345',
    city: 'Bjelovar',
    area: 1200.0,
    capacity: 4,
  },
  {
    id: 3,
    name: 'Villa Glamour',
    address: 'Bez imena',
    postalCode: '12345',
    city: 'Bjelovar',
    area: 5000.0,
    capacity: 12,
  },
  {
    id: 4,
    name: 'Litra i voda',
    address: 'Samuela Velikog 232',
    postalCode: '12345',
    city: 'Bjelovar',
    area: 5000.0,
    capacity: 12,
  },
];

const ListOfHomes = () => {
  return (
    <div style={{ padding: '20px' }}>
      <Typography variant="h4" gutterBottom>
        Dostupni društveni domovi
      </Typography>
      <Grid container spacing={2}>
        {mockHouses.map((house) => (
          <Grid item xs={12} sm={6} md={4} key={house.id}>
            <Link href={`/home/${house.id}`} passHref>
              <Card variant="outlined" className={styles.card}>
                <CardContent className={styles.cardContent}>
                  <Typography variant="h5" className={styles.cardTitle}>
                    {house.name}
                  </Typography>
                  <Typography className={styles.cardAddress} component={'div'}>
                    <strong>Ulica:</strong> {house.address}
                  </Typography>
                  <Typography className={styles.cardLocation} component={'div'}>
                    <strong>Mjesto: </strong>
                    {`${house.city}, ${house.postalCode}`}
                  </Typography>
                  <Typography className={styles.cardDetails} component={'div'}>
                    <strong>Kvadratura: </strong>
                    {`${house.area}m²`}
                  </Typography>
                  <Typography className={styles.cardDetails} component={'div'}>
                    <strong>Kapacitet ljudi: </strong>
                    {house.capacity}
                  </Typography>
                </CardContent>
                <CardActions>
                  <Typography variant="button" color="primary" className={styles.link}>
                    Provjeri dostupnost
                  </Typography>
                </CardActions>
              </Card>
            </Link>
          </Grid>
        ))}
      </Grid>
    </div>
  );
};

export default ListOfHomes;
