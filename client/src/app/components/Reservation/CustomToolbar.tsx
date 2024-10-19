import React, { useCallback } from 'react';
import { ToolbarProps } from 'react-big-calendar';

const CustomToolbar = (props: ToolbarProps) => {
  const goToBack = useCallback(() => {
    props.onNavigate('PREV');
  }, [props]);

  const goToNext = useCallback(() => {
    props.onNavigate('NEXT');
  }, [props]);

  const goToToday = useCallback(() => {
    props.onNavigate('TODAY');
  }, [props]);

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
