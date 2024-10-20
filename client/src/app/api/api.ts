import api from './instance';
import {
  ContractStatus,
  CreateReservation,
  LoginResponse,
  Pageable,
  PageResponse,
  RecordBookStatus,
  ReservationType,
} from '../types/types';
import { setItem } from './local-storage';
import { authorityKey, tokenKey } from '../lib/constants';
import { endpoints } from './constants';
import { formatDate } from '@/app/api/utils';

export type Credentials = {
  email: string;
  password: string;
};

export const authenticate = async (credentials: Credentials) => {
  const res = await api.post<LoginResponse>(endpoints.login, credentials);
  setItem(tokenKey, res.data.jwtToken.idToken);
  setItem(authorityKey, res.data.authority);
  return res.data;
};

export const createReservation = async (reservation: CreateReservation) => {
  return await api.post(endpoints.createReservation, reservation);
};

export type ReservationResponse = {
  reservationId: number;
  customerId: number;
  customerFirstName: string;
  customerLastName: string;
  communityHomePlanId: number;
  creationDate: string;
  reason: string;
  datetimeFrom: string;
  datetimeTo: string;
  bank: string;
  iban: string;
  approved: boolean;
  type: ReservationType;
};

export const getReservations = async (pageable: Pageable, approved?: boolean) => {
  const res = await api.get<PageResponse<ReservationResponse>>(endpoints.getReservations, {
    params: { ...pageable, approved },
  });
  return res.data;
};

export const getReservation = async (id: number) => {
  const res = await api.get(endpoints.getReservation(id));
  return res.data;
};

export const decideReservation = async (id: number, approve: boolean) => {
  return await api.patch(endpoints.decideReservation(id), null, {
    params: {
      approve,
    },
  });
};

export type ContractResponse = {
  id: number;
  customerFirstName: string;
  customerLastName: string;
  lease: number;
  downPayment: number;
  amenities: number;
  total: number;
  vat: number;
  status: ContractStatus;
};

export const getContracts = async (pageable: Pageable, status: ContractStatus | undefined) => {
  const params = {
    ...pageable,
    status,
  };
  const res = await api.get<PageResponse<ContractResponse>>(endpoints.getContracts, {
    params,
  });
  return res.data;
};

export const getContractDoc = async () => {
  const res = await api.get(endpoints.getContractDocument, {
    responseType: 'arraybuffer',
  });
  return res.data;
};

export const payContractUser = async (contractId: number) => {
  return await api.post(endpoints.payContract, { contractId });
};

export const signContractMayor = async (contractId: number) => {
  return await api.post(endpoints.signContractMayor, { contractId });
};

export const signContractUser = async (contractId: number) => {
  return await api.post(endpoints.signContractUser, { contractId });
};

export type CommunityHomeResponse = {
  id: number;
  name: string;
  address: string;
  postalCode: string;
  city: string;
  area: number;
  capacity: number;
};

export const getCommunityHomes = async () => {
  const res = await api.get<CommunityHomeResponse[]>(endpoints.getCommunityHomes);
  return res.data;
};

export type ReservationTimeRange = {
  start: string;
  end: string;
};

export const getOccupation = async (id: number, from: Date, to: Date) => {
  const params = {
    from: formatDate(from),
    to: formatDate(to),
  };
  const res = await api.get<ReservationTimeRange[]>(endpoints.getOccupation(id), {
    params,
  });
  return res.data;
};

export type UsernameResponse = {
  firstName: string;
  lastName: string;
};

export type RecordBookResponse = {
  recordBookId: number;
  contractId: number;
  custodianId: number;
  stateBefore: string;
  stateAfter: string;
  damage: string;
  inspectionDate: string;
  custodian: UsernameResponse;
  customer: UsernameResponse;
  reservationStart: string;
  reservationEnd: string;
  status: RecordBookStatus;
  communityHomeName: string;
};

export const getRecordsBooks = async (pageable: Pageable, status: RecordBookStatus | undefined) => {
  const params = {
    ...pageable,
    status,
  };
  const res = await api.get<PageResponse<RecordBookResponse>>(endpoints.getRecordBooks, {
    params,
  });
  return res.data;
};

export const updateRecordBook = async (id: number, data: object, type: 'before' | 'after') => {
  return await api.patch(endpoints.updateRecordBooks(id, type), data);
};
