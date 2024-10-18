import { z } from 'zod';
import dayjs from 'dayjs';

const mandatoryFieldMessage = 'Polje je obavezno';

export const schema = z
  .object({
    firstName: z.string().min(1, mandatoryFieldMessage),
    lastName: z.string().min(1, mandatoryFieldMessage),
    address: z.string().min(1, mandatoryFieldMessage),
    city: z.string().min(1, mandatoryFieldMessage),
    oib: z.string().min(1, mandatoryFieldMessage),
    mobilePhone: z.string().regex(/^\d+$/, 'Broj mobitela mora sadržavati samo brojeve'),
    bank: z.string().min(1, mandatoryFieldMessage),
    iban: z
      .string()
      .min(15, 'IBAN mora sadržavati najmanje 15 znakova')
      .max(34, 'IBAN može sadržavati najviše 34 znakova'),
    purpose: z.string().min(1, mandatoryFieldMessage),
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

export type ReserveFormData = z.infer<typeof schema>;
