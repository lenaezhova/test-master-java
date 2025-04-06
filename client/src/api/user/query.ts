import {useMutation, useQueryClient} from "react-query";
import {message} from "antd";
import {login, registration} from "./index";
import {getErrorData} from "../index";

const useLogin = () => {
  return useMutation(
    login,
    {
      onSuccess: () => {
        message.success('Вы успешно авторизовались!')
      },
      onError: (error) => {
        const errorData = getErrorData(error);
        message.error(errorData?.message || 'Произошла ошибка при авторизации')
      }
    }
  )
}

const useRegistration = () => {
  return useMutation(
    registration,
    {
      onSuccess: () => {
        message.success('Вы успешно зарегистировались!')
      },
      onError: (error) => {
        const errorData = getErrorData(error);
        message.error(errorData?.message || 'Произошла ошибка при регистрации')
      }
    }
  )
}

export {
  useLogin,
  useRegistration
}
