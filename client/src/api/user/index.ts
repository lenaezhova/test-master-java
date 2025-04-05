import $api from "../index";

export const getUser = async (values: {id: number}): Promise<{}> => {
  const {data} = await $api.get(`/user/${values.id}`);
  return data;
};
