'use client';

import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import { Card, CardActions, CardContent, Container, Grid, Typography } from '@mui/material';
import styles from './page.module.css';
import { CommunityHomeResponse, getCommunityHomes } from '@/app/api/api';

const ListOfHomes = () => {
  const [homes, setHomes] = useState<CommunityHomeResponse[]>([]);

  useEffect(() => {
    const fetchHomes = async () => {
      try {
        const data = await getCommunityHomes();
        setHomes(data);
      } catch (error) {
        console.error('Error fetching community homes:', error);
      }
    };
    fetchHomes();
  }, []);

  return (
    <Container className={styles.container}>
      <Typography variant="h3" gutterBottom>
        Dostupni društveni domovi
      </Typography>
      <Grid container spacing={2}>
        {homes.map((house) => (
          <Grid item xs={12} sm={6} md={4} key={house.id}>
            <Link href={`/homes/${house.id}`} passHref>
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
                    {`${house.postalCode}, ${house.city}`}
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
    </Container>
  );
};

export default ListOfHomes;
