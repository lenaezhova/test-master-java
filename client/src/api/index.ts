import axios from 'axios'
import {getAccessToken, removeAccessToken} from "../utils/tokens";
import {UserStore} from "../stores/UserStore/UserStore";
import {API_URL} from "../utils/const";
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

const $api = axios.create({
  baseURL: API_URL,
  withCredentials: true
})

export const axiosSetup = ($user: UserStore) => {
  $api.interceptors.request.use(
    (config) => {
      const token = getAccessToken();
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

          originalRequest.headers.Authorization = `Bearer ${getAccessToken()}`;
          processQueue(null, getAccessToken());

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
