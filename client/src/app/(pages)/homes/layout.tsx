import { ReactNode } from 'react';
import { ReservationContextProvider } from '../../contexts/ReservationContext';

type Props = {
  children: ReactNode;
};

const ReservationLayout = ({ children }: Props) => {
  return <ReservationContextProvider>{children}</ReservationContextProvider>;
};

export default ReservationLayout;
