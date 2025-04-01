import $api from "../index";
import {ITest, ITestsCreateModelRequest, ITestsDeleteModelRequest, ITestsUpdateModelRequest} from "./type";

export const getTests = async (): Promise<ITest[]> => {
  const {data} = await $api.get('/test');
  return data;
};

export const getTest = async (values: {id: number}): Promise<ITest> => {
  const {data} = await $api.get(`/test/${values.id}`);
  return data;
};

export const createNewTest = async (values: ITestsCreateModelRequest) => {
  const {data} = await $api.post('/test', values);
  return data;
};

export const updateTest = async ({id, ...values}: ITestsUpdateModelRequest) => {
  const {data} = await $api.put(`/test/${id}`, values);
  return data;
};

export const deleteTest = async (values: ITestsDeleteModelRequest) => {
  const {data} = await $api.delete(`/test/${values.id}`);
  return data;
};
