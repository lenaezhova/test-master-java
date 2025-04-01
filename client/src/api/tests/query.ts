import {QueryKey, useMutation, useQuery, useQueryClient} from "react-query";
import {createNewTest, deleteTest, getTests} from "./index";
import {BIG_STALE_TIME} from "../../utils/const";
import {message} from "antd";

export const getTestsQueryKey = (): QueryKey => ['tests'];

const useTests = () => {
  const queryKey = getTestsQueryKey();
  const queryClient = useQueryClient();

  return {
    invalidate: () => queryClient.invalidateQueries({queryKey}),
    ...useQuery(queryKey, getTests, {
      staleTime: BIG_STALE_TIME,
    })
  };
}

const useCreateTest = () => {
  const queryKey = getTestsQueryKey();
  const queryClient = useQueryClient();

  return useMutation(
    createNewTest,
    {
      onSuccess: () => {
        message.success('Тест создан успешно')
      },
      onSettled: async () => {
        await queryClient.invalidateQueries({queryKey})
      },
      onError: () => {
        message.error('Произошла ошибка при создании теста')
      }
    }
  )
}

const useDeleteTest = () => {
  const queryKey = getTestsQueryKey();
  const queryClient = useQueryClient();

  return useMutation(
    deleteTest,
    {
      onSuccess: () => {
        message.success('Тест удален успешно')
      },
      onSettled: async () => {
        await queryClient.invalidateQueries({queryKey})
      },
      onError: () => {
        message.error('Произошла ошибка при удалении теста')
      }
    }
  )
}

export {
  useTests,
  useCreateTest,
  useDeleteTest
}
