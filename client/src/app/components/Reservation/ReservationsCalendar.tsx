import React, { ComponentType, useCallback, useMemo, useState } from 'react';
import { Calendar, dateFnsLocalizer, ToolbarProps, View } from 'react-big-calendar';
import { format, parse, startOfWeek, getDay } from 'date-fns';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Container, Typography } from '@mui/material';
import { hr } from 'date-fns/locale/hr';
import { Reservation } from '@/app/components/Reservation/helper';
import CustomToolbar from '@/app/components/Reservation/CustomToolbar';

const locales = {
  hr,
};

const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek: () => startOfWeek(new Date(), { weekStartsOn: 1 }),
  getDay,
  locales,
});

interface CalendarProps {
  reservations: Reservation[];
}

const MyCalendar = ({ reservations }: CalendarProps) => {
  const [view, setView] = useState<View>('month');
  const [date, setDate] = useState(new Date());

  const events = useMemo(
    () =>
      reservations.map((reservation) => ({
        title: reservation.title,
        start: reservation.reservationStart,
        end: reservation.reservationEnd,
      })),
    [reservations]
  );

  const handleNavigate = useCallback((newDate: Date) => {
    setDate(newDate);
  }, []);

  const handleViewChange = useCallback((newView: View) => {
    setView(newView);
  }, []);

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Dostupnost doma
      </Typography>
      <Calendar
        localizer={localizer}
        events={events}
        startAccessor="start"
        endAccessor="end"
        style={{ height: 500 }}
        defaultView={view}
        date={date}
        views={['month']}
        onNavigate={handleNavigate}
        onView={handleViewChange}
        selectable={false}
        culture="hr"
        components={{
          toolbar: CustomToolbar as ComponentType<ToolbarProps<{ title: string; start: Date; end: Date }, object>>,
        }}
      />
    </Container>
  );
};

export default MyCalendar;
