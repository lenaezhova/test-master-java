import {QueryKey, useMutation, useQuery, useQueryClient} from "react-query";
import {message} from "antd";
import {getUser, login, registration} from "./index";
import {UserStore} from "../../stores/UserStore/UserStore";
import {getErrorData} from "../../utils/error";

export const getUserKey = ($user: UserStore): QueryKey => ['user', $user.item.id];

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
      onError: (error) => {
        const errorData = getErrorData(error);
        message.error(errorData?.message || 'Произошла ошибка при регистрации')
      }
    }
  )
}

export {
  useLogin,
  useRegistration,
}
