'use client';

import React, { useCallback, useMemo, useState } from 'react';
import { Accordion, AccordionDetails, AccordionSummary, Box, Typography } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import CancelOutlinedIcon from '@mui/icons-material/CancelOutlined';

type ContractType = 'active' | 'pending' | 'completed' | 'cancelled';
type ReservationType = 'meeting' | 'event' | 'personal';

type Contract = {
  id: number;
  customer: string;
  lease: number;
  downPayment: number;
  amenities: number;
  total: number;
  vat: number;
  status: ContractType;
};

type Reservation = {
  id: number;
  name: string;
  cause: string;
  date: string;
  startTime: string;
  endTime: string;
  status: string;
  type: ReservationType;
  stateBefore?: string;
  stateAfter?: string;
  officialApproved: boolean;
  mayorApproved: boolean;
};

const reservationsData: Reservation[] = [
  {
    id: 1,
    name: 'Conference Room Booking',
    cause: 'Business Meeting',
    date: '2024-10-22',
    startTime: '09:00 AM',
    endTime: '11:00 AM',
    status: 'upcoming',
    type: 'meeting',
    officialApproved: true,
    mayorApproved: false,
  },
  {
    id: 2,
    name: 'Birthday Party',
    cause: 'Family Event',
    date: '2024-11-15',
    startTime: '06:00 PM',
    endTime: '10:00 PM',
    status: 'completed',
    type: 'event',
    officialApproved: true,
    mayorApproved: true,
    stateBefore: 'Pristine condition',
    stateAfter: 'Some decorations left, but overall clean',
  },
];

const contractsData: Contract[] = [
  {
    id: 1,
    customer: 'John Doe',
    lease: 2000,
    downPayment: 500,
    amenities: 300,
    total: 2300,
    vat: 345,
    status: 'active',
  },
  {
    id: 2,
    customer: 'John Doe',
    lease: 1500,
    downPayment: 300,
    amenities: 200,
    total: 1700,
    vat: 255,
    status: 'completed',
  },
];

const CustomerReservationsPage: React.FC = () => {
  const [expanded, setExpanded] = useState<number | false>(false);

  const handleAccordionChange = useCallback((id: number) => {
    setExpanded((prev) => (prev === id ? false : id));
  }, []);

  const getContractForReservation = useCallback((reservationId: number) => {
    return contractsData.find((contract) => contract.id === reservationId);
  }, []);

  const filteredReservations = useMemo(() => reservationsData, []);

  return (
    <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh" bgcolor="#f0f0f0">
      <Box width="80%" maxWidth="800px" bgcolor="white" borderRadius={2} boxShadow={2} p={3}>
        <h1 style={{ textAlign: 'center' }}>Your Reservations</h1>

        {filteredReservations.map((reservation) => {
          const contract = getContractForReservation(reservation.id);

          return (
            <Accordion
              key={reservation.id}
              expanded={expanded === reservation.id}
              onChange={() => handleAccordionChange(reservation.id)}>
              <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                <Typography>{`${reservation.name} - ${reservation.status}`}</Typography>
              </AccordionSummary>
              <AccordionDetails style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                {/* Left Section: Reservation Details */}
                <Box display="flex" flexDirection="column" gap={2} width="70%">
                  <Typography variant="body1">
                    <strong>Cause:</strong> {reservation.cause}
                  </Typography>
                  <Typography variant="body1">
                    <strong>Date:</strong> {reservation.date}
                  </Typography>
                  <Typography variant="body1">
                    <strong>Time:</strong> {reservation.startTime} - {reservation.endTime}
                  </Typography>
                  <Typography variant="body1">
                    <strong>Type:</strong> {reservation.type}
                  </Typography>

                  {/* Optional State Before and After */}
                  {reservation.stateBefore && (
                    <Typography variant="body1">
                      <strong>State Before:</strong> {reservation.stateBefore}
                    </Typography>
                  )}
                  {reservation.stateAfter && (
                    <Typography variant="body1">
                      <strong>State After:</strong> {reservation.stateAfter}
                    </Typography>
                  )}

                  {contract ? (
                    <>
                      <Typography variant="body1">
                        <strong>Contract Status:</strong> {contract.status}
                      </Typography>
                      <Typography variant="body1">
                        <strong>Lease:</strong> ${contract.lease}
                      </Typography>
                      <Typography variant="body1">
                        <strong>Amenities:</strong> ${contract.amenities}
                      </Typography>
                      <Typography variant="body1">
                        <strong>Total (with VAT):</strong> ${contract.total + contract.vat}
                      </Typography>
                      <Typography variant="body1">
                        <strong>Down Payment:</strong> ${contract.downPayment}
                      </Typography>
                    </>
                  ) : (
                    <Typography variant="body2" color="error">
                      No contract information available.
                    </Typography>
                  )}
                </Box>

                <Box display="flex" flexDirection="column" alignItems="flex-end" gap={1} width="30%">
                  <Box display="flex" alignItems="center" gap={1}>
                    <Typography variant="body1">
                      <strong>Official Approval:</strong>
                    </Typography>
                    {reservation.officialApproved ? (
                      <CheckCircleOutlineIcon style={{ color: 'green' }} />
                    ) : (
                      <CancelOutlinedIcon style={{ color: 'red' }} />
                    )}
                  </Box>

                  <Box display="flex" alignItems="center" gap={1}>
                    <Typography variant="body1">
                      <strong>Mayor Approval:</strong>
                    </Typography>
                    {reservation.mayorApproved ? (
                      <CheckCircleOutlineIcon style={{ color: 'green' }} />
                    ) : (
                      <CancelOutlinedIcon style={{ color: 'red' }} />
                    )}
                  </Box>
                </Box>
              </AccordionDetails>
            </Accordion>
          );
        })}
      </Box>
    </Box>
  );
};

export default CustomerReservationsPage;
