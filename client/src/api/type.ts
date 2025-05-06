export type ErrorResponseData = {
  message?: string;
}

export interface ErrorResponse {
  code?: string;
  message?: string;
  response?: {
    data?: ErrorResponseData
  }
}
