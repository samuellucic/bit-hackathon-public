import React from 'react';
import { ToolbarProps } from 'react-big-calendar';

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

  return (
    <div className="rbc-toolbar">
      <span className="rbc-btn-group">
        <button onClick={goToToday}>Danas</button>
        <button onClick={goToBack}>Prethodni</button>
        <button onClick={goToNext}>Sljedeći</button>
      </span>
      <span className="rbc-toolbar-label">{props.label}</span>
    </div>
  );
};

export default CustomToolbar;
