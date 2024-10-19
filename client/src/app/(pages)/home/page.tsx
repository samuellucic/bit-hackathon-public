'use client';

import React, { useCallback, useEffect, useState } from 'react';
import { Button, Container, Stack, Typography } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import HomeIcon from '@mui/icons-material/Home';
import Image from 'next/image';
import styles from './page.module.css';
import bjelovarRotor from '../../../../public/images/bjelovar-rotor.jpeg';
import bjelovarZima from '../../../../public/images/Bjelovar-zima.jpeg';
import bjelovarPark from '../../../../public/images/bjelovar-park.jpg';
import { useRouter } from 'next/navigation';

const Home = () => {
  const images = [bjelovarRotor, bjelovarZima, bjelovarPark];
  const [currentIndex, setCurrentIndex] = useState(0);
  const router = useRouter();
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 3000);
    return () => clearInterval(interval);
  }, [images.length]);
  const handleReservationButtonClick = useCallback(() => {
    router.push('/homes');
  }, [router]);
  const handleViewReservationButtonClick = useCallback(() => {
    router.push('/login');
  }, [router]);

  return (
    <Container className={styles.container}>
      <Typography variant="h3" gutterBottom className={styles.title}>
        Društveni domovi Bjelovar
      </Typography>
      <Typography variant="subtitle1" className={styles.description}>
        Pregledajte društvene domove grada Bjelovara i u svega nekoliko klikova rezervirajte dom za Vaš događaj
      </Typography>
      <Stack direction="row" spacing={2} className={styles.buttonsContainer}>
        <Button
          variant="contained"
          className={styles.button}
          startIcon={<SearchIcon />}
          onClick={handleReservationButtonClick}>
          Rezervirajte dom
        </Button>
        <Button
          variant="contained"
          className={styles.button}
          startIcon={<HomeIcon />}
          onClick={handleViewReservationButtonClick}>
          Imate rezervaciju?
        </Button>
      </Stack>
      <div className={styles.imageContainer}>
        {images.map((image, index) => (
          <div key={index} className={`${styles.imageSlide} ${index === currentIndex ? styles.active : ''}`}>
            <Image src={image} alt={`Slide ${index + 1}`} className={styles.image} />
          </div>
        ))}
      </div>
    </Container>
  );
};

export default Home;
