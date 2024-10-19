'use client';
import React, { useCallback, useState } from 'react';
import { Box, Button, List, ListItem, ListItemText } from '@mui/material';

interface SearchItem {
  id: number;
  primaryText: string;
  secondaryText?: string;
}

interface SearchBarProps {
  // value: string;
  // onChange: (e: ChangeEvent<HTMLInputElement>) => void;
  // label?: string;
  items: SearchItem[];
  onItemClick: (item: SearchItem) => void;
  onNext: () => void;
  hasMore: boolean;
}

const ScrollBar: React.FC<SearchBarProps> = ({
  // value,
  // onChange,
  // label = 'Search',
  items,
  onItemClick,
  onNext,
  hasMore,
}) => {
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
      {/*<TextField label={label} variant="outlined" fullWidth value={value} onChange={onChange} />*/}

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

      {hasMore && (
        <Button fullWidth onClick={onNext}>
          Load more
        </Button>
      )}
    </Box>
  );
};

export default ScrollBar;
