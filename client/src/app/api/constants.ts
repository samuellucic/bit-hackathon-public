const users = '/users';
const reservations = '/reservations';
const contractAction = `/action/contract`;
const contracts = '/contracts';

const reservationEndpoints = {
  createReservation: reservations,
  getReservations: reservations,
  getReservation: (id: number) => `${reservations}/${id}`,
  decideReservation: (id: number) => `${reservations}/${id}`,
  getUsersReservations: (id: number) => `${users}/${id}/reservations`,
};

const contractsEndpoints = {
  getContracts: contracts,
  getContractDocument: `${contracts}/doc`,
  payContract: `${contractAction}/pay`,
  signContractMayor: `${contractAction}/sign-mayor`,
  signContractUser: `${contractAction}/sign-user`,
};

export const endpoints = {
  login: '/auth/authenticate',
  ...reservationEndpoints,
  ...contractsEndpoints,
} as const;

export type EndpointKey = (typeof endpoints)[keyof typeof endpoints];
