import { z } from 'zod';
import { mandatoryFieldMessage } from '@/app/lib/constants';

export const schema = z.object({
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
});

export type ReserveFormData = z.infer<typeof schema>;
