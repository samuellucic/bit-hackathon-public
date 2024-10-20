export type ThemeType = 'dark' | 'light';

export type JwtToken = {
  idToken: string;
};

export type Authority = 'ADMIN' | 'CUSTODIAN' | 'OFFICIAL' | 'CUSTOMER' | 'MAYOR';

export type LoginResponse = {
  jwtToken: JwtToken;
  authority: Authority;
};

export type ReservationType = 'NORMAL' | 'FUNERAL' | 'FIRE_DEPARTMENT' | 'OTHER';

export type ContractStatus = 'CREATED' | 'MAYOR_SIGNED' | 'PAYMENT_PENDING' | 'FINALIZED' | 'DECLINED';

export type RecordBookStatus =
  | 'CREATED'
  | 'FILLED_BEFORE'
  | 'FILLED_AFTER'
  | 'SIGNED'
  | 'DOWN_PAYMENT_RETURNED'
  | 'DOWN_PAYMENT_FORFEITED';

export type UserReservation = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  OIB: string;
  phone: string;
  city: string;
  address: string;
  postalCode: string;
};

export type CreateReservation = {
  // treba dodati optional AppUserRequest parametar u slucaju da user nije ulogiran
  user?: UserReservation;
  communityHomeId: number;
  reason: string;
  datetimeFrom: string;
  datetimeTo: string;
  bank?: string;
  iban?: string;
  type: ReservationType;
};

export type Pageable = {
  size: number;
  page: number;
};

export type PageResponse<T> = {
  items: T[];
  totalElements: number;
  totalPages: number;
};
