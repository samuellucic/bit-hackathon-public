import { createTheme } from '@mui/material';

export const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
  components: {
    MuiContainer: {
      defaultProps: {
        sx: {
          backgroundColor: 'black',
        },
      },
    },
    MuiTypography: {
      variants: [
        {
          props: { variant: 'h3' } /* component props */,
          style: {
            color: 'white',
          },
        },
      ],
    },
  },
});

export const lightTheme = createTheme({
  palette: {
    mode: 'light',
  },
  components: {
    MuiTypography: {
      variants: [
        {
          props: { variant: 'h3' } /* component props */,
          style: {
            color: 'black',
          },
        },
      ],
    },
  },
});
