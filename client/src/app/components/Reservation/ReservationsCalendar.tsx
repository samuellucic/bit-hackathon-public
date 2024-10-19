import React, { useState } from 'react';
import { Calendar, dateFnsLocalizer, View } from 'react-big-calendar';
import { format, parse, startOfWeek, getDay } from 'date-fns';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Container, Typography } from '@mui/material';
/* eslint-disable-next-line */
// @ts-ignore
import hrLocale from 'date-fns/locale/hr';
import { Reservation } from '@/app/components/Reservation/typings';
import CustomToolbar from '@/app/components/Reservation/CustomToolbar';

const locales = {
  hr: hrLocale,
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

  const events = reservations.map((reservation) => ({
    title: reservation.title,
    start: reservation.reservationStart,
    end: reservation.reservationEnd,
  }));

  const handleNavigate = (newDate: Date) => {
    setDate(newDate);
  };

  const handleViewChange = (newView: View) => {
    setView(newView);
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Reservation Calendar
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
          /* eslint-disable-next-line */
          // @ts-ignore
          toolbar: CustomToolbar,
        }}
      />
    </Container>
  );
};

export default MyCalendar;
