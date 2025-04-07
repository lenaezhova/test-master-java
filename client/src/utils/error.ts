import {ErrorResponse, ErrorResponseData} from "../api/type";

export const getErrorData = (error: any): ErrorResponseData => {
  return (error as ErrorResponse)?.response?.data
}
