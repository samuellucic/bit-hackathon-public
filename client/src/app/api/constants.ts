const users = '/users';
const reservations = '/reservations';
const contractAction = `/action/contract`;
const contracts = '/contracts';
const communityHomes = '/community-homes';
const recordBooks = `/record-books`;

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

const recordBooksEndpoints = {
  getRecordBooks: recordBooks,
};

const communityHomesEndpoints = {
  getCommunityHomes: communityHomes,
};

const availabilityEndpoints = {
  getOccupation: (id: number) => `/action/occupation${communityHomes}/${id}`,
};

export const endpoints = {
  login: '/auth/authenticate',
  ...reservationEndpoints,
  ...contractsEndpoints,
  ...communityHomesEndpoints,
  ...availabilityEndpoints,
  ...recordBooksEndpoints,
} as const;

export type EndpointKey = (typeof endpoints)[keyof typeof endpoints];
