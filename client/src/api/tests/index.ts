import $api from "../index";
import {ITest, ITestsCreateModelRequest} from "./type";

export const createNewTest = async (values: ITestsCreateModelRequest) => {
    const {data} = await $api.post('/tests', values);
    return data;
};

export const getTests = async (): Promise<ITest[]> => {
    const {data} = await $api.get('/tests');
    return data;
};
