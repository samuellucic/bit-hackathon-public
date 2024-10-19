'use client';
import React, { ChangeEvent, useCallback, useMemo, useState } from 'react';
import { Box, Button, Divider, List, ListItem, ListItemText, Paper, TextField, Typography } from '@mui/material';

type Reservation = {
  id: number;
  name: string;
  cause: string;
  date: string;
  startTime: string;
  endTime: string;
  status: string;
};

const reservations: Reservation[] = [
  {
    id: 1,
    name: 'John Doe',
    cause: 'Wedding',
    date: '2024-10-20',
    startTime: '10:00 AM',
    endTime: '5:00 PM',
    status: 'Pending',
  },
  {
    id: 2,
    name: 'Jane Smith',
    cause: 'Conference',
    date: '2024-11-15',
    startTime: '9:00 AM',
    endTime: '1:00 PM',
    status: 'Approved',
  },
  {
    id: 3,
    name: 'Mark Johnson',
    cause: 'Birthday Party',
    date: '2024-12-05',
    startTime: '2:00 PM',
    endTime: '10:00 PM',
    status: 'Pending',
  },
  {
    id: 4,
    name: 'Matej Ištuk',
    cause: 'DnD',
    date: '2025-12-05',
    startTime: '2:00 PM',
    endTime: '10:00 PM',
    status: 'Approved',
  },
];

export default function CommunityHomeReservations() {
  const [selectedReservation, setSelectedReservation] = useState<Reservation>(reservations[0]);
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchChange = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  }, []);

  const handleReservationClick = useCallback((reservation: Reservation) => {
    setSelectedReservation(reservation);
  }, []);

  const filteredReservations = useMemo(
    () =>
      reservations.filter(
        (reservation) =>
          reservation.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
          reservation.cause.toLowerCase().includes(searchTerm.toLowerCase())
      ),
    [searchTerm]
  );

  return (
    <Box display="flex" height="100vh">
      <Paper elevation={2} sx={{ width: '30%', overflowY: 'auto' }}>
        <Box p={2}>
          <TextField label="Search" variant="outlined" fullWidth value={searchTerm} onChange={handleSearchChange} />
        </Box>
        <List>
          {filteredReservations.map((reservation) => (
            <ListItem key={reservation.id} onClick={() => handleReservationClick(reservation)} component={'li'}>
              <ListItemText
                primary={reservation.name}
                secondary={`${reservation.cause} - ${reservation.date} - ${reservation.startTime} to ${reservation.endTime}`}
              />
            </ListItem>
          ))}
        </List>
      </Paper>
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
          </Box>
        )}

        {selectedReservation && (
          <Box display="flex" flexDirection="column" alignItems="flex-end" ml={2}>
            <Button variant="contained" color="primary" sx={{ backgroundColor: 'blue', mb: 1, minWidth: '120px' }}>
              Accept
            </Button>
            <Button variant="contained" color="secondary" sx={{ backgroundColor: 'red', minWidth: '120px' }}>
              Deny
            </Button>
          </Box>
        )}
      </Box>
    </Box>
  );
}
