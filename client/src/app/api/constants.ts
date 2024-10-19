const users = '/api/users';
const reservations = '/api/reservations';
const contractAction = `/api/action/contract`;
const contracts = '/api/contracts';

const reservationEndpoints = {
  createReservation: reservations,
  getReservations: reservations,
  getReservation: (id: number) => `${reservations}/${id}`,
  approveReservation: (id: number) => `${reservations}/${id}/approve`,
  getUsersReservations: (id: number) => `${users}/${id}/reservations`,
};

const contractsEndpoints = {
  getContractDocument: `${contracts}/doc`,
  payContract: `${contractAction}/pay`,
  signContractMayor: `${contractAction}/sign-major`,
  signContractUser: `${contractAction}/sign-user`,
};

export const endpoints = {
  login: '/auth/authenticate/',
  ...reservationEndpoints,
  ...contractsEndpoints,
} as const;

export type EndpointKey = (typeof endpoints)[keyof typeof endpoints];
