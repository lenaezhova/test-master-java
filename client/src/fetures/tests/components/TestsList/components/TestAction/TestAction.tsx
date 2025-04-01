import React from 'react';
import s from './TestAction.module.scss';
import {Button, Popconfirm} from 'antd';
import {DeleteOutlined, EditOutlined} from '@ant-design/icons';
import {useCreateTest, useDeleteTest} from "../../../../../../api/tests/query";
import TestsManageModalBtn from "../../../TestsManageModalBtn/TestsManageModalBtn";

interface TestActionProps {
  id?: number;
}

const TestAction = ({id}: TestActionProps) => {
  const {isLoading, mutateAsync} = useDeleteTest();

  const handleDeleteTest = async () => {
    try {
      await mutateAsync({id});
    } catch (e) {
      console.log(e)
    }
  };

  return (
    <div className={s.testAction}>
      <Popconfirm
        title="Удалить тест"
        description="Вы уверены, что хотите удалить тест?"
        onConfirm={handleDeleteTest}
        okText="Да"
        cancelText="Нет"
        okButtonProps={{
          loading: isLoading
        }}
      >
        <Button
          icon={<DeleteOutlined />}
          type={'text'}
          danger
          loading={isLoading}
        />
      </Popconfirm>
      <TestsManageModalBtn
        id={id}
        icon={<EditOutlined />}
        type={'text'}
      />
    </div>
  );
};

export default TestAction;
