'use client';
import React, { useCallback, useContext, useMemo, useState } from 'react';
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
import ScrollBar from '../../components/ScrollBar/ScrollBar';
import usePaginated from '../../hooks/usePaginated';
import { defaultPageSize } from '../../lib/constants';
import { ContractStatus, Pageable } from '../../types/types';
import { getContractDoc, getContracts, payContractUser, signContractMayor, signContractUser } from '../../api/api';
import styles from './page.module.css';
import { UserContext } from '../../contexts/UserContext';

type Contract = {
  id: number;
  customer: string;
  lease: number;
  downPayment: number;
  total: number;
  vat: number;
  status: ContractStatus;
};

type SelectItem = {
  value: ContractStatus;
  label: string;
};

const selectItems: SelectItem[] = [
  { value: 'CREATED', label: 'Created' },
  { value: 'DECLINED', label: 'Declined' },
  { value: 'FINALIZED', label: 'Finalized' },
  { value: 'PAYMENT_PENDING', label: 'Payment pending' },
  { value: 'MAYOR_SIGNED', label: 'Major signed' },
];

export default function ContractManagement() {
  const { authority } = useContext(UserContext);

  const [status, setStatus] = useState<ContractStatus>();
  const [selectedContract, setSelectedContract] = useState<Contract>();

  const fetch = useCallback(
    async (pageable: Pageable) => {
      const { items, ...rest } = await getContracts(pageable, status);
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
    },
    [status]
  );

  const [contracts, getNext, hasMore, refresh] = usePaginated<Contract>({ fetch, size: defaultPageSize });

  const handleContractClick = useCallback(
    (contractItem: { id: number; primaryText: string; secondaryText?: string }) => {
      const contract = contracts.find((c) => c.id === contractItem.id);
      if (contract) {
        setSelectedContract(contract);
      }
    },
    [contracts]
  );

  const handleDownload = useCallback(async () => {
    const doc = await getContractDoc();

    const url = window.URL.createObjectURL(new Blob([doc]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `Document.doc`);
    document.body.appendChild(link);
    link.click();
    link.parentNode!.removeChild(link);
  }, []);

  const handleFilterChange = useCallback((e: SelectChangeEvent) => {
    setStatus(e.target.value as ContractStatus);
  }, []);

  const onMayorSign = useCallback(async () => {
    if (selectedContract) {
      await signContractMayor(selectedContract?.id);
    }
    await refresh();
    setSelectedContract(undefined);
  }, [refresh, selectedContract]);

  const onCustomerSign = useCallback(async () => {
    if (selectedContract) {
      await signContractUser(selectedContract?.id);
    }
    await refresh();
    setSelectedContract(undefined);
  }, [refresh, selectedContract]);

  const onCustomerPay = useCallback(async () => {
    if (selectedContract) {
      await payContractUser(selectedContract?.id);
    }
    await refresh();
    setSelectedContract(undefined);
  }, [refresh, selectedContract]);

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
      <Box sx={{ display: 'flex', flexDirection: 'column', width: '30%' }}>
        <FormControl sx={{ mb: 2 }}>
          <InputLabel>Status Filter</InputLabel>
          <Select value={status} label="Status Filter" onChange={handleFilterChange} variant="outlined">
            {selectItems.map(({ label, value }) => (
              <MenuItem key={label} value={value}>
                {label}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <Paper elevation={2} sx={{ overflowY: 'auto' }}>
          <ScrollBar items={contractItems} onItemClick={handleContractClick} onNext={getNext} hasMore={hasMore} />
        </Paper>
      </Box>
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

            <Box sx={{ display: 'flex', gap: '0.5rem' }}>
              {authority && ['MAYOR', 'ADMIN'].includes(authority) && selectedContract.status === 'CREATED' && (
                <Button variant="contained" onClick={onMayorSign}>
                  Sign contract
                </Button>
              )}
              {authority && ['CUSTOMER', 'ADMIN'].includes(authority) && selectedContract.status === 'MAYOR_SIGNED' && (
                <Button variant="contained" onClick={onCustomerSign}>
                  Sign contract
                </Button>
              )}
              {authority &&
                ['CUSTOMER', 'ADMIN'].includes(authority) &&
                selectedContract.status === 'PAYMENT_PENDING' && (
                  <Button variant="contained" onClick={onCustomerPay}>
                    Pay
                  </Button>
                )}
              <Button variant="contained" onClick={handleDownload}>
                Download Contract
              </Button>
            </Box>
          </Box>
        )}
      </Box>
    </Container>
  );
}
