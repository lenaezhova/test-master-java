import $api from "../index";
import {CreateUserRequest, IUser, LoginRequest, TokenResponse} from "./type";
import axios from "axios";
import {API_URL} from "../../utils/const";

const userApiPrefix = '/users'

export const getUser = async (values: { id: string }): Promise<IUser> => {
  const {data} = await $api.get(userApiPrefix + `/${values.id}`);
  return data;
};

export const getCurrentUser = async (): Promise<IUser> => {
  const {data} = await $api.get(userApiPrefix + `/current`);
  return data;
};

export const updateRefreshToken = async (): Promise<TokenResponse> => {
  const {data} = await $api.post(userApiPrefix + `/auth/refresh`);
  return data;
};

export const login = async (values: LoginRequest): Promise<{ data: TokenResponse }> => {
  return await $api.post(userApiPrefix + `/auth/login`, values);
};

export const logout = async () => {
  return await $api.post(userApiPrefix + `/auth/logout`);
};

export const registration = async (values: CreateUserRequest): Promise<{ data: TokenResponse }> => {
  return await $api.post(userApiPrefix + `/auth/registration`, values);
};
