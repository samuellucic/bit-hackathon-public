'use client';

import React, { useCallback, useContext, useEffect, useState } from 'react';
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
import { downPaymentRecordBook, getRecordsBooks, signRecordBook, updateRecordBook } from '../../api/api';
import usePaginated from '../../hooks/usePaginated';
import { defaultPageSize } from '../../lib/constants';
import { UserContext } from '../../contexts/UserContext';

type RecordBook = {
  id: number;
  customerName: string;
  custodianName: string;
  reservationStart: string;
  reservationEnd: string;
  status: RecordBookStatus;
  conditionBefore?: string;
  conditionAfter?: string;
  damage?: string;
  communityHomeName: string;
};

type SelectItem = { status: RecordBookStatus | undefined; label: string };

const selectItems: SelectItem[] = [
  { status: 'CREATED', label: 'Stvoreno' },
  { status: 'FILLED_BEFORE', label: 'Ispunio korisnik' },
  { status: 'FILLED_AFTER', label: 'Ispunio domar' },
  { status: 'SIGNED', label: 'Potpisano' },
  { status: 'DOWN_PAYMENT_RETURNED', label: 'Jamčevina vraćenja' },
  { status: 'DOWN_PAYMENT_FORFEITED', label: 'Jamčevina zadržana' },
  { status: undefined, label: 'All' },
];

const RecordBooksPage: React.FC = () => {
  const { authority } = useContext(UserContext);

  const [status, setStatus] = useState<RecordBookStatus>();
  const [expanded, setExpanded] = useState<number | false>(false);

  const [conditionBefore, setConditionBefore] = useState<string>();
  const [conditionAfter, setConditionAfter] = useState<string>();
  const [damage, setDamage] = useState<string>();

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
        items: items.map(({ recordBookId, customer, custodian, stateBefore, stateAfter, ...rest }) => ({
          ...rest,
          id: recordBookId,
          customerName: `${customer.firstName} ${customer.lastName}`,
          custodianName: `${custodian.firstName} ${custodian.lastName}`,
          conditionBefore: stateBefore,
          conditionAfter: stateAfter,
        })),
      };
    },
    [status]
  );

  const [recordBooks, getNext, hasMore, refresh] = usePaginated<RecordBook>({ fetch, size: defaultPageSize });

  const onConditionBeforeChange = useCallback((conditionBefore: string) => {
    setConditionBefore(conditionBefore);
  }, []);
  const onConditionAfterChange = useCallback((conditionAfter: string) => {
    setConditionAfter(conditionAfter);
  }, []);
  const onDamageChange = useCallback((damage: string) => {
    setDamage(damage);
  }, []);

  const resetFormField = useCallback(() => {
    setDamage(undefined);
    setConditionAfter(undefined);
    setConditionBefore(undefined);
  }, []);

  const handleBeforeSubmit = useCallback(
    async (id: number) => {
      await updateRecordBook(
        id,
        {
          stateBefore: conditionBefore,
        },
        'before'
      );
      await refresh();
      resetFormField();
    },
    [conditionBefore, refresh, resetFormField]
  );
  const handleAfterSubmit = useCallback(
    async (id: number) => {
      await updateRecordBook(
        id,
        {
          stateAfter: conditionAfter,
          damage,
        },
        'after'
      );
      await refresh();
      resetFormField();
    },
    [conditionAfter, damage, refresh, resetFormField]
  );
  const handleSignRecordBook = useCallback(
    async (id: number) => {
      await signRecordBook(id);
      await refresh();
      resetFormField();
    },
    [refresh, resetFormField]
  );

  const handleReturnDownPayment = useCallback(
    async (id: number, returned: boolean) => {
      await downPaymentRecordBook(id, returned);
      await refresh();
      resetFormField();
    },
    [refresh, resetFormField]
  );

  useEffect(() => {
    refresh();
  }, [status]);

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
              {recordBook.status !== 'CREATED' && (
                <>
                  <Typography variant="body1">
                    <strong>Stanje prije preuzimanja:</strong> {`${recordBook.conditionBefore}`}
                  </Typography>
                </>
              )}
              {!['CREATED', 'FILLED_BEFORE'].includes(recordBook.status) && (
                <>
                  <Typography variant="body1">
                    <strong>Stanje poslije preuzimanja:</strong> {`${recordBook.conditionBefore}`}
                  </Typography>
                  <Typography variant="body1">
                    <strong>Šteta:</strong> {`${recordBook.damage}`}
                  </Typography>
                </>
              )}
              {authority !== 'CUSTOMER' && (
                <>
                  {recordBook.status === 'FILLED_BEFORE' && (
                    <>
                      <TextareaAutosize
                        minRows={4}
                        placeholder="Ispunite podatke o stanju poslije preuzimanja"
                        onChange={(e) => onConditionAfterChange(e.target.value)}
                        style={{
                          width: '100%',
                          resize: 'none',
                          fontSize: '1rem',
                          padding: '0.5rem',
                          border: '1px solid #ccc',
                          borderRadius: '0.25rem',
                        }}
                      />
                      <TextareaAutosize
                        minRows={4}
                        placeholder="Ispunite podatke o potencijalnoj šteti"
                        onChange={(e) => onDamageChange(e.target.value)}
                        style={{
                          width: '100%',
                          resize: 'none',
                          fontSize: '1rem',
                          padding: '0.5rem',
                          border: '1px solid #ccc',
                          borderRadius: '0.25rem',
                        }}
                      />
                      <Button variant="contained" onClick={() => handleAfterSubmit(recordBook.id)}>
                        Prijavi stanje poslije
                      </Button>
                    </>
                  )}
                </>
              )}
              {recordBook.status === 'CREATED' && (
                <>
                  <TextareaAutosize
                    minRows={4}
                    placeholder="Ispunite podatke o stanju prije preuzimanja"
                    onChange={(e) => onConditionBeforeChange(e.target.value)}
                    style={{
                      width: '100%',
                      resize: 'none',
                      fontSize: '1rem',
                      padding: '0.5rem',
                      border: '1px solid #ccc',
                      borderRadius: '0.25rem',
                    }}
                  />
                  <Button variant="contained" onClick={() => handleBeforeSubmit(recordBook.id)}>
                    Prijavi stanje prije
                  </Button>
                </>
              )}
              {authority === 'CUSTOMER' && recordBook.status === 'FILLED_AFTER' && (
                <Button variant="contained" onClick={() => handleSignRecordBook(recordBook.id)}>
                  Potpiši zapisnik
                </Button>
              )}
              {authority === 'OFFICIAL' && recordBook.status === 'SIGNED' && (
                <Box sx={{ display: 'flex', gap: '0.5rem' }}>
                  <Button variant="contained" onClick={() => handleReturnDownPayment(recordBook.id, true)}>
                    Vrati jamčevinu
                  </Button>
                  <Button variant="contained" onClick={() => handleReturnDownPayment(recordBook.id, false)}>
                    Zadrži jamčevinu
                  </Button>
                </Box>
              )}
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
