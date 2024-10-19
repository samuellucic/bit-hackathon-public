import React from 'react';
import { Button, Container, Stack, Typography } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import HomeIcon from '@mui/icons-material/Home';
import styles from './page.module.css';
import bjelovarDroneShot from '../../../../public/images/Bjelovar-zima.jpeg';
import Image from 'next/image';

const Home = () => {
  return (
    <Container className={styles.container}>
      <Typography variant="h3" gutterBottom className={styles.title}>
        Društveni domovi Bjelovar
      </Typography>

      <Typography variant="body1" className={styles.description}>
        Pregledajte društvene domove grada Bjelovara i u svega nekoliko klikova rezervirajte dom za Vaš događaj
      </Typography>
      <Stack direction="row" spacing={2} className={styles.buttonsContainer}>
        <Button variant="contained" className={styles.button} startIcon={<SearchIcon />}>
          Rezervirajte dom
        </Button>
        <Button variant="contained" className={styles.button} startIcon={<HomeIcon />}>
          Imate rezervaciju ?
        </Button>
      </Stack>
      <Image src={bjelovarDroneShot} alt="Bjelovar Home Page Image" className={styles.image} />
    </Container>
  );
};

export default Home;
