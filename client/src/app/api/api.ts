import api from './instance';
import { CreateReservation, JwtToken, Pageable, PageResponse, ReservationType } from '../types/types';
import { setItem } from './local-storage';
import { tokenKey } from '../lib/constants';
import { endpoints } from './constants';

export type Credentials = {
  email: string;
  password: string;
};

export const authenticate = async (credentials: Credentials) => {
  const res = await api.post<JwtToken>(endpoints.login, credentials);
  setItem(tokenKey, res.data.idToken);
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

export const getReservations = async (pageable: Pageable) => {
  const res = await api.get<PageResponse<ReservationResponse>>(endpoints.getReservations, {
    params: pageable,
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

export const getContractDoc = async () => {
  return await api.get(endpoints.getContractDocument);
};

export const payContract = async (contractId: number) => {
  return await api.post(endpoints.payContract, { contractId });
};

export const signContractMayor = async (contractId: number) => {
  return await api.post(endpoints.signContractMayor, { contractId });
};

export const signContractUser = async (contractId: number) => {
  return await api.post(endpoints.signContractUser, { contractId });
};
