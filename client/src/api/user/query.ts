import {QueryKey, useMutation, useQuery, useQueryClient} from "react-query";
import {message} from "antd";
import {getCurrentUser, getUser, login, registration} from "./index";
import {UserStore} from "../../stores/UserStore/UserStore";
import {getErrorData} from "../../utils/error";

const useLogin = () => {
  return useMutation(
    login,
    {
      onError: (error) => {
        const errorData = getErrorData(error);
        message.error(errorData?.message || 'Произошла ошибка при авторизации')
      }
    }
  )
}

const useGetUser = ($user: UserStore) => {
  return useMutation(
    () => getCurrentUser(),
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
  useGetUser
}
