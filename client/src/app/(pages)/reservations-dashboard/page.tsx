'use client';
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import styles from './page.module.css';
import ScrollBar from '../../components/ScrollBar/ScrollBar'; // Import the reusable ScrollBar component
import {
  Box,
  Button,
  Container,
  Divider,
  FormControl,
  InputLabel,
  MenuItem,
  Paper,
  Select,
  SelectChangeEvent,
  Typography,
} from '@mui/material';
import usePaginated from '../../hooks/usePaginated';
import { Pageable, ReservationType } from '../../types/types';
import { decideReservation, getReservations } from '../../api/api';
import { defaultPageSize } from '../../lib/constants';

type Reservation = {
  id: number;
  name: string;
  cause: string;
  date: string;
  startTime: string;
  endTime: string;
  status: string;
  type: ReservationType;
};

export default function CommunityHomeReservations() {
  const [selectedReservation, setSelectedReservation] = useState<Reservation>();
  const [filterStatus, setFilterStatus] = useState<string>('notDecided');

  const fetch = useCallback(
    async (pageable: Pageable) => {
      let approved;
      if (filterStatus === 'approved') {
        approved = true;
      } else if (filterStatus === 'denied') {
        approved = false;
      }

      const { items, ...rest } = await getReservations(pageable, approved);
      return {
        ...rest,
        items: items.map(
          ({
            reservationId,
            customerFirstName,
            customerLastName,
            reason,
            creationDate,
            datetimeFrom,
            datetimeTo,
            type,
            approved,
          }) => ({
            id: reservationId,
            name: `${customerFirstName} ${customerLastName}`,
            cause: reason,
            date: creationDate,
            startTime: datetimeFrom,
            endTime: datetimeTo,
            type: type,
            status: approved === null ? 'Not decided' : approved ? 'Approved' : 'Denied',
          })
        ),
      };
    },
    [filterStatus]
  );

  const [reservations, getNext, hasMore, refresh] = usePaginated<Reservation>({ fetch, size: defaultPageSize });

  useEffect(() => {
    refresh();
  }, [filterStatus]);

  const handleReservationClick = useCallback(
    (reservationItem: { id: number; primaryText: string; secondaryText?: string }) => {
      const reservation = reservations.find((r) => r.id === reservationItem.id);
      if (reservation) {
        setSelectedReservation(reservation);
      }
    },
    [reservations]
  );

  const onDecision = useCallback(
    async (approved: boolean) => {
      if (selectedReservation) {
        await decideReservation(selectedReservation.id, approved);
      }
      setSelectedReservation(undefined);
      await refresh();
    },
    [selectedReservation, refresh]
  );

  const onApprove = useCallback(() => onDecision(true), [onDecision]);
  const onDeny = useCallback(() => onDecision(false), [onDecision]);

  const reservationItems = useMemo(
    () =>
      reservations.map((reservation) => ({
        id: reservation.id,
        primaryText: reservation.name,
        secondaryText: `${reservation.cause} - ${reservation.date} - ${reservation.startTime} to ${reservation.endTime}`,
      })),
    [reservations]
  );

  const handleFilterChange = (event: SelectChangeEvent<string>) => {
    setFilterStatus(event.target.value);
    setSelectedReservation(undefined);
  };

  return (
    <Container className={styles.container}>
      {/* Filter and Scrollable List */}
      <Box display="flex" flexDirection="column" width="30%" height="100vh" mb={2}>
        {/* Filter Dropdown */}
        <FormControl sx={{ minWidth: 200, mb: 2 }}>
          <InputLabel>Status Filter</InputLabel>
          <Select value={filterStatus} label="Status Filter" onChange={handleFilterChange} variant="outlined">
            <MenuItem value="approved">Approved</MenuItem>
            <MenuItem value="denied">Denied</MenuItem>
            <MenuItem value="notDecided">Not decided</MenuItem>
          </Select>
        </FormControl>

        {/* Scrollable List */}
        <Paper elevation={2} sx={{ overflowY: 'auto', flexGrow: 1 }}>
          <ScrollBar items={reservationItems} onItemClick={handleReservationClick} onNext={getNext} hasMore={hasMore} />
        </Paper>
      </Box>

      <Divider orientation="vertical" flexItem />

      <Box p={2} flex={1} display="flex" justifyContent="space-between" alignItems="flex-start">
        {selectedReservation && (
          <Box flexGrow={1}>
            <Typography variant="h6">{selectedReservation.name}</Typography>
            <Typography variant="subtitle1">{selectedReservation.cause}</Typography>
            <Typography variant="body2">Date: {selectedReservation.date}</Typography>
            <Typography variant="body2">
              {selectedReservation.startTime} - {selectedReservation.endTime}
            </Typography>
            <Typography variant="body2">Status: {selectedReservation.status}</Typography>
            <Typography variant="body2">Type: {selectedReservation.type}</Typography>
          </Box>
        )}

        {/* Conditionally render Approve/Deny buttons only when filterStatus is 'notDecided' */}
        {selectedReservation && filterStatus === 'notDecided' && (
          <Box display="flex" flexDirection="column" alignItems="flex-end" ml={2}>
            <Button
              onClick={onApprove}
              variant="contained"
              color="primary"
              sx={{ backgroundColor: 'blue', mb: 1, minWidth: '120px' }}>
              Accept
            </Button>
            <Button
              onClick={onDeny}
              variant="contained"
              color="secondary"
              sx={{ backgroundColor: 'red', minWidth: '120px' }}>
              Deny
            </Button>
          </Box>
        )}
      </Box>
    </Container>
  );
}
