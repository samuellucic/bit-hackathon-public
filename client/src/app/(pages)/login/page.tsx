'use client';
import React, { useCallback, useState } from 'react';
import { Box, Button, Link, Paper, TextField, Typography } from '@mui/material';

export default function Page() {
  const [isLoginMode, setIsLoginMode] = useState(true);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

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
        console.log('Register - Email:', email, 'Password:', password);
      }
    },
    [isLoginMode, email, password, confirmPassword]
  );

  const toggleMode = useCallback(() => {
    setIsLoginMode((prevMode) => !prevMode);
    setEmail('');
    setPassword('');
    setConfirmPassword('');
  }, []);

  const handleEmailChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  }, []);

  const handlePasswordChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  }, []);

  const handleConfirmPasswordChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    setConfirmPassword(e.target.value);
  }, []);

  const handleLinkClick = useCallback(
    (e: React.MouseEvent<HTMLAnchorElement>) => {
      e.preventDefault();
      toggleMode();
    },
    [toggleMode]
  );

  return (
    <Box display="flex" justifyContent="center" alignItems="center" height="100vh" bgcolor="background.default">
      <Paper elevation={3} sx={{ padding: 4, width: 300 }}>
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
              onChange={handleEmailChange}
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
              onChange={handlePasswordChange}
              required
            />
          </Box>
          {!isLoginMode && (
            <Box mb={2}>
              <TextField
                label="Confirm Password"
                type="password"
                variant="outlined"
                fullWidth
                value={confirmPassword}
                onChange={handleConfirmPasswordChange}
                required
              />
            </Box>
          )}
          <Button type="submit" variant="contained" color="primary" fullWidth>
            {isLoginMode ? 'Login' : 'Register'}
          </Button>
        </form>
        <Box mt={2} textAlign="center">
          <Typography variant="body2">
            {isLoginMode ? "Don't have an account?" : 'Already have an account?'}{' '}
            <Link href="#" onClick={handleLinkClick}>
              {isLoginMode ? 'Register' : 'Login'}
            </Link>
          </Typography>
        </Box>
      </Paper>
    </Box>
  );
}
