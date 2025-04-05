import $api from "../index";
import {JwtTokenPair} from "./type";

export const getUser = async (values: { id: number }): Promise<{}> => {
  const {data} = await $api.get(`/users/${values.id}`);
  return data;
};

export const updateRefreshToken = async (values: { refreshToken: string }): Promise<JwtTokenPair> => {
  const {data} = await $api.post(`/users/auth/refresh`, values);
  return data;
};
