import $api from "../index";
import {CreateUserRequest, JwtTokenPair, LoginRequest, LogoutRequest} from "./type";
import axios from "axios";
import {API_URL} from "../../utils/const";

const userApiPrefix = '/users'

export const getUser = async (values: { id: number }): Promise<{}> => {
  const {data} = await $api.get(userApiPrefix + `/${values.id}`);
  return data;
};

export const updateRefreshToken = async (values: { refreshToken: string }): Promise<JwtTokenPair> => {
  const {data} = await $api.post(userApiPrefix + `/auth/refresh`, values);
  return data;
};

export const login = async (values: LoginRequest): Promise<{
  data: JwtTokenPair,
}> => {
  return await axios.post(API_URL + userApiPrefix + `/auth/login`, values);
};

export const logout = async (values: LogoutRequest) => {
  return await axios.post(API_URL + userApiPrefix + `/auth/logout`, values);
};

export const registration = async (values: CreateUserRequest): Promise<{
  data: JwtTokenPair,
}> => {
  return await axios.post(API_URL + userApiPrefix + `/auth/registration`, values);
};
