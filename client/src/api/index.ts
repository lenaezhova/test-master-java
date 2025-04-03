import axios from 'axios'
type AuthResponse = any;

export const API_URL = process.env.REACT_APP_API_URL
export const CLIENT_URL= process.env.REACT_APP_CLIENT_URL

const $api = axios.create({
  baseURL: API_URL + '/api'
})

// $api.interceptors.request.use((config) => {
//   config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`
//   return config;
// })
//
// $api.interceptors.response.use((config) => {
//   return config;
// }, async (error) => {
//   const originalRequest = error.config;
//   if(error.response.status === 401 && error.config && !error.config._isRetry){
//     originalRequest._isRetry = true;
//     try {
//       const response = await axios.get<AuthResponse>(`${API_URL}/refresh`, {withCredentials: true})
//       localStorage.setItem('token', response.data.accessToken)
//       return $api.request(originalRequest);
//     } catch (e){
//     }
//     throw error;
//   }
// })

export default $api;
