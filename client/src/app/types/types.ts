export type ThemeType = 'dark' | 'light';

export type JwtToken = {
  idToken: string;
};

export type ReservationType = 'NORMAL' | 'FUNERAL' | 'FIRE_DEPARTMENT' | 'OTHER';

export type CreateReservation = {
  communityHomePlanId: number;
  reason: string;
  datetimeFrom: string;
  datetimeTo: string;
  bank: string;
  iban: string;
  type: ReservationType;
};
