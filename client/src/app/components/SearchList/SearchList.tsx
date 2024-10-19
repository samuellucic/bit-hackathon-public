'use client';
import React, { ChangeEvent, useCallback, useState } from 'react';
import { Box, List, ListItem, ListItemText, TextField } from '@mui/material';

interface SearchItem {
  id: number;
  primaryText: string;
  secondaryText?: string;
}

interface SearchBarProps {
  value: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  label?: string;
  items: SearchItem[];
  onItemClick: (item: SearchItem) => void;
}

const SearchBar: React.FC<SearchBarProps> = ({ value, onChange, label = 'Search', items, onItemClick }) => {
  const [hoveredItem, setHoveredItem] = useState<number | null>(null);
  const [selectedItem, setSelectedItem] = useState<number | null>(null);

  const handleMouseEnter = useCallback((id: number) => {
    setHoveredItem(id);
  }, []);

  const handleMouseLeave = useCallback(() => {
    setHoveredItem(null);
  }, []);

  const handleItemClick = useCallback(
    (item: SearchItem) => {
      setSelectedItem(item.id);
      onItemClick(item);
    },
    [onItemClick]
  );

  return (
    <Box p={2}>
      <TextField label={label} variant="outlined" fullWidth value={value} onChange={onChange} />

      <List sx={{ marginTop: '1rem' }}>
        {items.map((item) => (
          <ListItem
            key={item.id}
            onMouseEnter={() => handleMouseEnter(item.id)}
            onMouseLeave={handleMouseLeave}
            onClick={() => handleItemClick(item)}
            sx={{
              cursor: 'pointer',
              backgroundColor:
                selectedItem === item.id ? 'lightblue' : hoveredItem === item.id ? 'lightgray' : 'transparent',
              marginBottom: '0.5rem',
              transition: 'background-color 0.3s ease',
            }}>
            <ListItemText primary={item.primaryText} secondary={item.secondaryText} />
          </ListItem>
        ))}
      </List>
    </Box>
  );
};

export default SearchBar;
