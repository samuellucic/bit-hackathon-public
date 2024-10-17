import axios from 'axios';
import { getItem } from './local-storage';

const baseApiPath = `${process.env.NEXT_PUBLIC_BE_SERVICE_URL}/api`;

const commonHeader = {
  'Content-Type': 'application/json',
};

export const getAuthorizationHeader = () => {
  const token = getItem('auth-id-token');
  return { Authorization: token ? `Bearer ${token}` : undefined };
};

const api = axios.create({
  baseURL: baseApiPath,
  headers: {
    ...commonHeader,
  },
});

api.interceptors.request.use(
  (config) => {
    const authorization = getAuthorizationHeader().Authorization;
    if (authorization) {
      config.headers.Authorization = authorization;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
