import $api from "../index";
import {ITest, ITestsCreateModelRequest, ITestsDeleteModelRequest} from "./type";

export const getTests = async (): Promise<ITest[]> => {
  const {data} = await $api.get('/tests');
  return data;
};

export const createNewTest = async (values: ITestsCreateModelRequest) => {
  const {data} = await $api.post('/tests', values);
  return data;
};

export const deleteTest = async (values: ITestsDeleteModelRequest) => {
  const {data} = await $api.delete(`/tests/${values.id}`);
  return data;
};
