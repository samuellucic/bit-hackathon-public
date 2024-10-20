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
  dateOfIssue: new Date(0),
  timeFrom: new Date(0),
  timeTo: new Date(0),
  setDateOfIssue: () => {},
  setTimeFrom: () => {},
  setTimeTo: () => {},
});

type Props = {
  children: ReactNode;
};

export const ReservationContextProvider = ({ children }: Props) => {
  const [dateOfIssue, setDateOfIssue] = useState<Date>(new Date(0));
  const [timeFrom, setTimeFrom] = useState<Date>(new Date(0));
  const [timeTo, setTimeTo] = useState<Date>(new Date(0));

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
