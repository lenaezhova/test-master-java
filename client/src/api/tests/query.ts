import {QueryKey, useMutation, useQuery, useQueryClient, UseQueryOptions} from "react-query";
import {createNewTest, deleteTest, getTest, getTests, updateTest} from "./index";
import {BIG_STALE_TIME} from "../../utils/const";
import {message} from "antd";

export const getTestQueryKey = (id?: number): QueryKey => `test-${id}`;
export const getTestsQueryKey = (id?: number): QueryKey => ['tests', id];

const useTest = (id: number, options?: any) => {
  const queryKey = getTestQueryKey(id);

  return useQuery(queryKey, () => getTest({id}), options)
}

const useTests = () => {
  const queryKey = getTestsQueryKey();

  return useQuery(queryKey, getTests, {
    staleTime: BIG_STALE_TIME,
  })
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

const useUpdateTest = () => {
  const queryKey = getTestsQueryKey();
  const queryClient = useQueryClient();

  return useMutation(
    updateTest,
    {
      onSuccess: () => {
        message.success('Тест успешно обновлен')
      },
      onSettled: async () => {
        await queryClient.invalidateQueries({queryKey})
      },
      onError: () => {
        message.error('Произошла ошибка при обновлении теста')
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
  useTest,
  useCreateTest,
  useUpdateTest,
  useDeleteTest
}
