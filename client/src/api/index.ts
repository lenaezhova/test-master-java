import axios from 'axios'
import {ErrorResponse, ErrorResponseData} from "./type";
import {getAccessToken, getRefreshToken, removeAccessToken, setAccessToken} from "../utils/tokens";
import {stores} from "../stores/stores";
import {UserStore} from "../stores/UserStore/UserStore";
type AuthResponse = any;

let isRefreshing = false;
let pendingRequests = [];

const processQueue = (error, token = null) => {
  pendingRequests.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });
  pendingRequests = [];
};

export const API_URL_BASE = process.env.REACT_APP_API_URL
export const API_URL = process.env.REACT_APP_API_URL +  '/api'
export const CLIENT_URL= process.env.REACT_APP_CLIENT_URL

export const getErrorData = (error: any): ErrorResponseData => {
  return (error as ErrorResponse)?.response?.data
}

const $api = axios.create({
  baseURL: API_URL
})

export const axiosSetup = ($user: UserStore) => {
  $api.interceptors.request.use(
    (config) => {
      const token = $user.accessToken;
      const isLogoutRequest = config.url.endsWith('/logout');

      if (token && !isLogoutRequest) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => Promise.reject(error)
  );

  $api.interceptors.response.use(
    (response) => response,
    async (error) => {
      const originalRequest = error.config;
      const isRefreshTokenRequest = originalRequest.url?.endsWith('/refresh');

      if (error.response?.status === 401 && !originalRequest._retry && !isRefreshTokenRequest) {
        if (isRefreshing) {
          return new Promise((resolve, reject) => {
            pendingRequests.push({ resolve, reject });
          }).then((token) => {
            originalRequest.headers.Authorization = `Bearer ${token}`;
            return $api.request(originalRequest);
          });
        }

        originalRequest._retry = true;
        isRefreshing = true;

        try {
          removeAccessToken();
          delete originalRequest.headers['Authorization'];

          await $user.checkAuth();

          originalRequest.headers.Authorization = `Bearer ${$user.accessToken}`;
          processQueue(null, $user.accessToken);

          return $api.request(originalRequest);
        } catch (refreshError) {
          processQueue(refreshError, null);
          await $user.logout();

          return Promise.reject(refreshError);
        } finally {
          isRefreshing = false;
        }
      }

      return Promise.reject(error);
    }
  );
}

export default $api;
