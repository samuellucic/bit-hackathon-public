'use client';
import React, { useCallback, useState } from 'react';
import { Box, Button, Grid, Link, Paper, TextField, Typography } from '@mui/material';

export default function Page() {
  const [isLoginMode, setIsLoginMode] = useState(true);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [oib, setOib] = useState('');
  const [phoneNumber, setPhoneNumber] = useState('');
  const [address, setAddress] = useState('');
  const [postalCode, setPostalCode] = useState('');
  const [city, setCity] = useState('');

  const handleSubmit = useCallback(
    (e: { preventDefault: () => void }) => {
      e.preventDefault();
      if (isLoginMode) {
        console.log('Login - Email:', email, 'Password:', password);
      } else {
        if (password !== confirmPassword) {
          console.log("Passwords don't match");
          return;
        }
        console.log('Register - Data:', {
          firstName,
          lastName,
          oib,
          phoneNumber,
          address,
          postalCode,
          city,
          email,
          password,
        });
      }
    },
    [isLoginMode, email, password, confirmPassword, firstName, lastName, oib, phoneNumber, address, postalCode, city]
  );

  const toggleMode = useCallback(() => {
    setIsLoginMode((prevMode) => !prevMode);
    setEmail('');
    setPassword('');
    setConfirmPassword('');
  }, []);

  const handleChange =
    (setter: React.Dispatch<React.SetStateAction<string>>) => (e: React.ChangeEvent<HTMLInputElement>) =>
      setter(e.target.value);

  return (
    <Box display="flex" justifyContent="center" alignItems="center" height="100vh" bgcolor="background.default">
      <Paper elevation={3} sx={{ padding: 4, width: 400 }}>
        <Typography variant="h5" align="center" gutterBottom>
          {isLoginMode ? 'Login' : 'Register'}
        </Typography>
        <form onSubmit={handleSubmit}>
          <Box mb={2}>
            <TextField
              label="Email"
              type="email"
              variant="outlined"
              fullWidth
              value={email}
              onChange={handleChange(setEmail)}
              required
            />
          </Box>
          <Box mb={2}>
            <TextField
              label="Password"
              type="password"
              variant="outlined"
              fullWidth
              value={password}
              onChange={handleChange(setPassword)}
              required
            />
          </Box>
          {!isLoginMode && (
            <>
              <Box mb={2}>
                <TextField
                  label="Confirm Password"
                  type="password"
                  variant="outlined"
                  fullWidth
                  value={confirmPassword}
                  onChange={handleChange(setConfirmPassword)}
                  required
                />
              </Box>
              {/* Additional fields for registration */}
              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <TextField
                    label="First Name"
                    type="text"
                    variant="outlined"
                    fullWidth
                    value={firstName}
                    onChange={handleChange(setFirstName)}
                    required
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    label="Last Name"
                    type="text"
                    variant="outlined"
                    fullWidth
                    value={lastName}
                    onChange={handleChange(setLastName)}
                    required
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    label="OIB"
                    type="text"
                    variant="outlined"
                    fullWidth
                    value={oib}
                    onChange={handleChange(setOib)}
                    required
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    label="Phone Number"
                    type="tel"
                    variant="outlined"
                    fullWidth
                    value={phoneNumber}
                    onChange={handleChange(setPhoneNumber)}
                    required
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    label="Address"
                    type="text"
                    variant="outlined"
                    fullWidth
                    value={address}
                    onChange={handleChange(setAddress)}
                    required
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    label="Postal Code"
                    type="text"
                    variant="outlined"
                    fullWidth
                    value={postalCode}
                    onChange={handleChange(setPostalCode)}
                    required
                  />
                </Grid>
                <Grid item xs={6}>
                  <TextField
                    label="City"
                    type="text"
                    variant="outlined"
                    fullWidth
                    value={city}
                    onChange={handleChange(setCity)}
                    required
                  />
                </Grid>
              </Grid>
            </>
          )}
          <Button type="submit" variant="contained" color="primary" fullWidth sx={{ mt: 2 }}>
            {isLoginMode ? 'Login' : 'Register'}
          </Button>
        </form>
        <Box mt={2} textAlign="center">
          <Typography variant="body2">
            {isLoginMode ? "Don't have an account?" : 'Already have an account?'}{' '}
            <Link href="#" onClick={toggleMode}>
              {isLoginMode ? 'Register' : 'Login'}
            </Link>
          </Typography>
        </Box>
      </Paper>
    </Box>
  );
}
