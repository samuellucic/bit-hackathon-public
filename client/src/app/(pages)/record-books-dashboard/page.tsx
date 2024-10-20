'use client';

import React, { useCallback, useEffect, useState } from 'react';
import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Button,
  TextareaAutosize,
  Typography,
} from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Pageable, RecordBookStatus } from '../../types/types';
import { getRecordsBooks } from '../../api/api';
import usePaginated from '../../hooks/usePaginated';
import { defaultPageSize } from '../../lib/constants';

type RecordBook = {
  id: number;
  customerName: string;
  custodianName: string;
  reservationStart: string;
  reservationEnd: string;
  status: RecordBookStatus;
  conditionBefore?: string;
  conditionAfter?: string;
  communityHomeName: string;
  damage?: string;
};

type SelectItem = { status: RecordBookStatus | undefined; label: string };

const selectItems: SelectItem[] = [
  { status: 'CREATED', label: 'Created' },
  { status: 'DOWN_PAYMENT_FORFEITED', label: 'Down payment forfeited' },
  { status: 'SIGNED', label: 'Signed' },
  { status: 'DOWN_PAYMENT_RETURNED', label: 'Down payment returned' },
  { status: undefined, label: 'All' },
];

const RecordBooksPage: React.FC = () => {
  const [status, setStatus] = useState<RecordBookStatus>();
  const [expanded, setExpanded] = useState<number | false>(false);

  const toggleViewingStatus = useCallback(
    (status: RecordBookStatus | undefined) => {
      setStatus(status);
      setExpanded(false);
    },
    [status]
  );

  const fetch = useCallback(
    async (pageable: Pageable) => {
      const { items, ...rest } = await getRecordsBooks(pageable, status);
      return {
        ...rest,
        items: items.map(({ customer, custodian, ...rest }) => ({
          ...rest,
          customerName: `${customer.firstName} ${customer.lastName}`,
          custodianName: `${custodian.firstName} ${custodian.lastName}`,
        })),
      };
    },
    [status]
  );

  const [recordBooks, getNext, hasMore, refresh] = usePaginated<RecordBook>({ fetch, size: defaultPageSize });

  useEffect(() => {
    refresh();
  }, [status]);

  const handleSubmitCondition = useCallback((id: number, field: string) => {
    console.log(`Submitted ${field} for record book ID: ${id}`);
  }, []);

  return (
    <Box display="flex" justifyContent="center" alignItems="center" height="100vh" bgcolor="#f0f0f0">
      <Box width="80%" bgcolor="white" borderRadius={2} boxShadow={2} p={3}>
        <h1 style={{ textAlign: 'center' }}>Zapisnici</h1>

        <Box mb={2} display="flex" justifyContent="center" gap={2}>
          {selectItems.map(({ status, label }) => (
            <Button key={label} variant="outlined" onClick={() => toggleViewingStatus(status)}>
              {label}
            </Button>
          ))}
        </Box>

        {recordBooks.map((recordBook) => (
          <Accordion
            key={recordBook.id}
            expanded={expanded === recordBook.id}
            onChange={() => setExpanded(expanded === recordBook.id ? false : recordBook.id)}>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Typography>{`${recordBook.customerName} (${recordBook.status})`}</Typography>
            </AccordionSummary>
            <AccordionDetails style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
              <Typography variant="body1">
                <strong>Društveni dom:</strong> {recordBook.communityHomeName}
              </Typography>
              <Typography variant="body1">
                <strong>Domar:</strong> {`${recordBook.custodianName}`}
              </Typography>
              <Typography variant="body1">
                <strong>Rezervacija:</strong> {`${recordBook.reservationStart} to ${recordBook.reservationEnd}`}
              </Typography>
              <TextareaAutosize
                minRows={4}
                placeholder="Enter Condition Before"
                onChange={(e) => handleInputChange(recordBook.id, 'conditionBefore', e.target.value)}
                style={{
                  width: '100%',
                  resize: 'none',
                  fontSize: '1rem',
                  padding: '0.5rem',
                  border: '1px solid #ccc',
                  borderRadius: '0.25rem',
                }}
              />
              <Button variant="contained" onClick={() => handleSubmitCondition(recordBook.id, 'Condition Before')}>
                Submit Condition Before
              </Button>
            </AccordionDetails>
          </Accordion>
        ))}
        {hasMore && (
          <Button fullWidth onClick={getNext}>
            Load more
          </Button>
        )}
      </Box>
    </Box>
  );
};

export default RecordBooksPage;
