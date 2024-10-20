import { z } from 'zod';

export const schema = z.object({
  firstName: z.string(),
  lastName: z.string(),
  email: z.string(),
  password: z.string(),
  address: z.string(),
  city: z.string(),
  oib: z.string(),
  mobilePhone: z.string(),
  bank: z.string(),
  iban: z.string(),
  purpose: z.string(),
});

export type ReserveFormData = z.infer<typeof schema>;
