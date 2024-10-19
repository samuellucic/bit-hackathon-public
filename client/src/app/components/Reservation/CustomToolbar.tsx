import React from 'react';
import { ToolbarProps, View } from 'react-big-calendar';

const CustomToolbar = (props: ToolbarProps) => {
  const goToBack = () => {
    props.onNavigate('PREV');
  };

  const goToNext = () => {
    props.onNavigate('NEXT');
  };

  const goToToday = () => {
    props.onNavigate('TODAY');
  };

  const handleViewChange = (view: View) => {
    props.onView(view);
  };

  return (
    <div className="rbc-toolbar">
      <span className="rbc-btn-group">
        <button onClick={goToToday}>Danas</button>
        <button onClick={goToBack}>Prethodni</button>
        <button onClick={goToNext}>Sljedeći</button>
      </span>
      <span className="rbc-toolbar-label">{props.label}</span>
      <span className="rbc-btn-group">
        <button onClick={() => handleViewChange('month')}>Mjesec</button>
      </span>
    </div>
  );
};

export default CustomToolbar;
