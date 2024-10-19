import { createTheme } from '@mui/material';

export const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  },
  components: {
    MuiContainer: {
      defaultProps: {
        sx: {
          backgroundColor: '#3a3a3d',
        },
      },
    },
    MuiStack: {
      defaultProps: {
        sx: {
          backgroundColor: '#3a3a3d',
        },
      },
    },
    MuiTypography: {
      variants: [
        {
          props: { variant: 'h3' },
          style: {
            color: 'white',
          },
        },
        {
          props: { variant: 'body1' },
          style: {
            color: 'white',
          },
        },
      ],
    },
    MuiSvgIcon: {
      defaultProps: {
        sx: {
          color: 'white',
        },
      },
    },
    MuiButton: {
      defaultProps: {
        sx: {
          color: 'white',
          backgroundColor: '#a9a9a9',
          '&:hover': {
            backgroundColor: '#808080',
          },
        },
      },
    },
    MuiAppBar: {
      defaultProps: {
        sx: {
          backgroundColor: '#131315',
        },
      },
    },
  },
});

export const lightTheme = createTheme({
  palette: {
    mode: 'light',
  },
  components: {
    MuiContainer: {
      defaultProps: {
        sx: {
          backgroundColor: 'white',
          color: 'black',
        },
      },
    },
    MuiStack: {
      defaultProps: {
        sx: {
          backgroundColor: 'white',
          color: 'black',
        },
      },
    },
    MuiTypography: {
      variants: [
        {
          props: { variant: 'h3' },
          style: {
            color: 'black',
          },
        },
        {
          props: { variant: 'subtitle1' },
          style: {
            color: 'black',
          },
        },
      ],
    },
    MuiAppBar: {
      defaultProps: {
        sx: {
          backgroundColor: '#1976d2',
        },
      },
    },
  },
});
