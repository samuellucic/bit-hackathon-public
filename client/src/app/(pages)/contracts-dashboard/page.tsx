'use client';
import React, { ChangeEvent, useCallback, useMemo, useState } from 'react';
import { Box, Button, Container, Divider, Paper, Typography } from '@mui/material';
import SearchBar from '../../components/SearchList/SearchList';
import styles from './page.module.css';

type Contract = {
  id: number;
  customer: string;
  lease: number;
  downPayment: number;
  amenities: number;
  total: number;
  vat: number;
  status: string;
};

const contracts: Contract[] = [
  {
    id: 1,
    customer: 'John Doe',
    lease: 5000,
    downPayment: 1000,
    amenities: 200,
    total: 6200,
    vat: 15,
    status: 'Pending',
  },
  {
    id: 2,
    customer: 'Jane Smith',
    lease: 7500,
    downPayment: 1500,
    amenities: 500,
    total: 9200,
    vat: 20,
    status: 'Approved',
  },
  {
    id: 3,
    customer: 'Mark Johnson',
    lease: 3000,
    downPayment: 800,
    amenities: 100,
    total: 3900,
    vat: 10,
    status: 'Pending',
  },
  {
    id: 4,
    customer: 'Matej Ištuk',
    lease: 6000,
    downPayment: 1200,
    amenities: 300,
    total: 7500,
    vat: 18,
    status: 'Approved',
  },
];

export default function ContractManagement() {
  const [selectedContract, setSelectedContract] = useState<Contract | null>(contracts[0]);
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchChange = useCallback((e: ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  }, []);

  const handleContractClick = useCallback(
    (contractItem: { id: number; primaryText: string; secondaryText?: string }) => {
      const contract = contracts.find((c) => c.id === contractItem.id);
      if (contract) {
        setSelectedContract(contract);
      }
    },
    []
  );

  const handleDownload = useCallback(() => {
    alert('Download functionality goes here!');
  }, []);

  const filteredContracts = useMemo(
    () =>
      contracts.filter(
        (contract) =>
          contract.customer.toLowerCase().includes(searchTerm.toLowerCase()) ||
          contract.status.toLowerCase().includes(searchTerm.toLowerCase())
      ),
    [searchTerm]
  );

  const contractItems = useMemo(
    () =>
      filteredContracts.map((contract) => ({
        id: contract.id,
        primaryText: contract.customer,
        secondaryText: `Lease: $${contract.lease} - Total: $${contract.total} - Status: ${contract.status}`,
      })),
    [filteredContracts]
  );

  return (
    <Container className={styles.container}>
      <Paper elevation={2} sx={{ width: '30%', overflowY: 'auto' }}>
        <SearchBar
          value={searchTerm}
          onChange={handleSearchChange}
          items={contractItems}
          onItemClick={handleContractClick}
        />
      </Paper>
      <Divider orientation="vertical" flexItem />
      <Box p={2} flex={1}>
        {selectedContract && (
          <Box display="flex" justifyContent="space-between" alignItems="center">
            <Box>
              <Typography variant="h6">Customer: {selectedContract.customer}</Typography>
              <Typography variant="subtitle1">Lease Amount: ${selectedContract.lease}</Typography>
              <Typography variant="body2">Down Payment: ${selectedContract.downPayment}</Typography>
              <Typography variant="body2">Amenities Cost: ${selectedContract.amenities}</Typography>
              <Typography variant="body2">Total Amount: ${selectedContract.total}</Typography>
              <Typography variant="body2">VAT: {selectedContract.vat}%</Typography>
              <Typography variant="body2">Status: {selectedContract.status}</Typography>
            </Box>

            <Button variant="contained" onClick={handleDownload}>
              Download Contract
            </Button>
          </Box>
        )}
      </Box>
    </Container>
  );
}
