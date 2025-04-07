export type ErrorResponseData = {
  message?: string;
  code?: string;
  timestamp?: string;
}

export interface ErrorResponse {
  code?: string;
  message?: string;
  response?: {
    data?: ErrorResponseData
  }
}
