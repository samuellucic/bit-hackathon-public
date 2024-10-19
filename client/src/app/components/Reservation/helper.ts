import { z } from 'zod';
import dayjs from 'dayjs';
import { mandatoryFieldMessage } from '@/app/lib/constants';

export type Reservation = {
  reservationStart: Date;
  reservationEnd: Date;
  title: string;
};

export const schema = z
  .object({
    dateOfIssue: z.date().refine((date) => !isNaN(date.getTime()), mandatoryFieldMessage),
    timeFrom: z.date().refine((date) => !isNaN(date.getTime()), mandatoryFieldMessage),
    timeTo: z.date().refine((date) => !isNaN(date.getTime()), mandatoryFieldMessage),
  })
  .refine((data) => (data.timeFrom && data.timeTo ? data.timeTo > data.timeFrom : true), {
    message: 'Početak korištenja mora biti prije kraja korištenja',
    path: ['timeTo'],
  })
  .refine(
    (data) => {
      if (data.dateOfIssue && data.timeTo) {
        const dateOfIssue = dayjs(data.dateOfIssue);
        const timeTo = dayjs(data.timeTo);
        return timeTo.diff(dateOfIssue, 'day') >= 8;
      }
      return true;
    },
    {
      message: 'Datum izdavanja mora biti najmanje 8 dana prije datuma početka korištenja',
      path: ['dateOfIssue'],
    }
  );

export type ReserveDateTimeData = z.infer<typeof schema>;
