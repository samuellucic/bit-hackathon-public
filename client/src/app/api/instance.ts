import axios from 'axios';
import { getItem } from './local-storage';
import { tokenKey } from '../lib/constants';

const baseApiPath = `${process.env.NEXT_PUBLIC_BE_SERVICE_URL}/api`;

const commonHeader = {
  'Content-Type': 'application/json',
};

export const getAuthorizationHeader = () => {
  const token = getItem(tokenKey);
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

api.interceptors.response.use(
  (value) => value,
  (error) => {
    console.log(error);

    return Promise.reject(error);
  }
);

export default api;
