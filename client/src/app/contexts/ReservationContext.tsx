'use client';

import { createContext, ReactNode, useState } from 'react';

export type ReservationContextType = {
  dateOfIssue: Date;
  timeFrom: Date;
  timeTo: Date;
  setDateOfIssue: (date: Date) => void;
  setTimeFrom: (date: Date) => void;
  setTimeTo: (date: Date) => void;
};

export const ReservationContext = createContext<ReservationContextType>({
  dateOfIssue: new Date(2024, 10, 10),
  timeFrom: new Date(2024, 10, 23),
  timeTo: new Date(2024, 10, 25),
  setDateOfIssue: () => {},
  setTimeFrom: () => {},
  setTimeTo: () => {},
});

type Props = {
  children: ReactNode;
};

export const ReservationContextProvider = ({ children }: Props) => {
  const [dateOfIssue, setDateOfIssue] = useState<Date>(new Date(2024, 10, 10));
  const [timeFrom, setTimeFrom] = useState<Date>(new Date(2024, 10, 23));
  const [timeTo, setTimeTo] = useState<Date>(new Date(2024, 10, 25));

  return (
    <ReservationContext.Provider
      value={{
        dateOfIssue,
        timeFrom,
        timeTo,
        setDateOfIssue,
        setTimeFrom,
        setTimeTo,
      }}>
      {children}
    </ReservationContext.Provider>
  );
};
