'use client';
import React, { useCallback, useMemo, useState } from 'react';
import { Box, Button, Container, Divider, Paper, Typography } from '@mui/material';
import ScrollBar from '../../components/ScrollBar/ScrollBar';
import usePaginated from '../../hooks/usePaginated';
import { defaultPageSize } from '../../lib/constants';
import { ContractType, Pageable } from '../../types/types';
import { getContracts } from '../../api/api';
import styles from './page.module.css';

type Contract = {
  id: number;
  customer: string;
  lease: number;
  downPayment: number;
  total: number;
  vat: number;
  status: ContractType;
};

export default function ContractManagement() {
  const [selectedContract, setSelectedContract] = useState<Contract>();

  const fetch = useCallback(async (pageable: Pageable) => {
    const { items, ...rest } = await getContracts(pageable);
    return {
      ...rest,
      items: items.map(({ id, customerFirstName, customerLastName, lease, downPayment, total, vat, status }) => ({
        id,
        customer: `${customerFirstName} ${customerLastName}`,
        lease,
        downPayment,
        total,
        vat,
        status,
      })),
    };
  }, []);

  const [contracts, getNext, hasMore] = usePaginated<Contract>({ fetch, size: defaultPageSize });

  const handleContractClick = useCallback(
    (contractItem: { id: number; primaryText: string; secondaryText?: string }) => {
      const contract = contracts.find((c) => c.id === contractItem.id);
      if (contract) {
        setSelectedContract(contract);
      }
    },
    [contracts]
  );

  const handleDownload = useCallback(() => {
    alert('Download functionality goes here!');
  }, []);

  const contractItems = useMemo(
    () =>
      contracts.map((contract) => ({
        id: contract.id,
        primaryText: contract.customer,
        secondaryText: `Lease: $${contract.lease} - Total: $${contract.total} - Status: ${contract.status}`,
      })),
    [contracts]
  );

  return (
    <Container className={styles.container}>
      <Paper elevation={2} sx={{ width: '30%', overflowY: 'auto' }}>
        <ScrollBar items={contractItems} onItemClick={handleContractClick} onNext={getNext} hasMore={hasMore} />
      </Paper>
      <Divider orientation="vertical" flexItem />
      <Box p={2} flex={1}>
        {selectedContract && (
          <Box display="flex" justifyContent="space-between" alignItems="center">
            <Box>
              <Typography variant="h6">Customer: {selectedContract.customer}</Typography>
              <Typography variant="subtitle1">Lease Amount: ${selectedContract.lease}</Typography>
              <Typography variant="body2">Down Payment: ${selectedContract.downPayment}</Typography>
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
