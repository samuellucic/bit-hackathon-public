'use client';

import React, { useCallback, useState } from 'react';
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

type RecordBook = {
  id: number;
  date: string;
  customerName: string;
  status: 'upcoming' | 'completed';
  conditionBefore?: string;
  conditionAfter?: string;
};

const recordBooksData: RecordBook[] = [
  { id: 1, date: '2024-10-20', customerName: 'John Doe', status: 'upcoming' },
  {
    id: 2,
    date: '2024-10-21',
    customerName: 'Jane Smith',
    status: 'completed',
    conditionBefore: 'Some wear and tear.',
    conditionAfter: 'Clean and tidy.',
  },
  { id: 3, date: '2024-10-22', customerName: 'Alice Johnson', status: 'upcoming' },
  {
    id: 4,
    date: '2024-10-23',
    customerName: 'Bob Brown',
    status: 'completed',
    conditionBefore: 'Very clean.',
    conditionAfter: 'Left it in great shape.',
  },
];

const RecordBooksPage: React.FC = () => {
  const [recordBooks, setRecordBooks] = useState<RecordBook[]>(recordBooksData);
  const [viewingStatus, setViewingStatus] = useState<'all' | 'upcoming' | 'completed'>('all');
  const [expanded, setExpanded] = useState<number | false>(false);

  const toggleViewingStatus = useCallback((status: 'upcoming' | 'completed' | 'all') => {
    setViewingStatus(status);
    setExpanded(false);
  }, []);

  const filteredRecordBooks = useCallback(() => {
    return recordBooks.filter((recordBook) => {
      if (viewingStatus === 'upcoming') return recordBook.status === 'upcoming';
      if (viewingStatus === 'completed') return recordBook.status === 'completed';
      return true;
    });
  }, [recordBooks, viewingStatus]);

  const sortedUpcomingRecordBooks = useCallback(() => {
    return filteredRecordBooks()
      .filter((recordBook) => recordBook.status === 'upcoming')
      .sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());
  }, [filteredRecordBooks]);

  const handleInputChange = useCallback((id: number, field: 'conditionBefore' | 'conditionAfter', value: string) => {
    setRecordBooks((prev) =>
      prev.map((recordBook) => (recordBook.id === id ? { ...recordBook, [field]: value } : recordBook))
    );
  }, []);

  const handleSubmitCondition = useCallback((id: number, field: string) => {
    console.log(`Submitted ${field} for record book ID: ${id}`);
  }, []);

  return (
    <Box display="flex" justifyContent="center" alignItems="center" height="100vh" bgcolor="#f0f0f0">
      <Box width="80%" maxWidth="37.5rem" bgcolor="white" borderRadius={2} boxShadow={2} p={3}>
        <h1 style={{ textAlign: 'center' }}>Community Home Record Books</h1>

        <Box mb={2} display="flex" justifyContent="center" gap={2}>
          <Button
            variant={viewingStatus === 'upcoming' ? 'contained' : 'outlined'}
            onClick={() => toggleViewingStatus('upcoming')}>
            Upcoming
          </Button>
          <Button
            variant={viewingStatus === 'completed' ? 'contained' : 'outlined'}
            onClick={() => toggleViewingStatus('completed')}>
            Completed
          </Button>
          <Button
            variant={viewingStatus === 'all' ? 'contained' : 'outlined'}
            onClick={() => toggleViewingStatus('all')}>
            All
          </Button>
        </Box>

        {viewingStatus === 'upcoming'
          ? sortedUpcomingRecordBooks().map((recordBook) => (
              <Accordion
                key={recordBook.id}
                expanded={expanded === recordBook.id}
                onChange={() => setExpanded(expanded === recordBook.id ? false : recordBook.id)}>
                <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                  <Typography>{`${recordBook.date} - ${recordBook.customerName} (${recordBook.status})`}</Typography>
                </AccordionSummary>
                <AccordionDetails style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
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
            ))
          : filteredRecordBooks().map((recordBook) => (
              <Accordion
                key={recordBook.id}
                expanded={expanded === recordBook.id}
                onChange={() => setExpanded(expanded === recordBook.id ? false : recordBook.id)}>
                <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                  <Typography>{`${recordBook.date} - ${recordBook.customerName} (${recordBook.status})`}</Typography>
                </AccordionSummary>
                <AccordionDetails style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                  <Typography variant="body1">
                    <strong>Condition Before:</strong> {recordBook.conditionBefore}
                  </Typography>
                  <TextareaAutosize
                    minRows={4}
                    placeholder="Enter Condition After"
                    onChange={(e) => handleInputChange(recordBook.id, 'conditionAfter', e.target.value)}
                    style={{
                      width: '100%',
                      resize: 'none',
                      fontSize: '1rem',
                      padding: '0.5rem',
                      border: '1px solid #ccc',
                      borderRadius: '0.25rem',
                    }}
                  />
                  <Button variant="contained" onClick={() => handleSubmitCondition(recordBook.id, 'Condition After')}>
                    Submit Condition After
                  </Button>
                </AccordionDetails>
              </Accordion>
            ))}
      </Box>
    </Box>
  );
};

export default RecordBooksPage;
