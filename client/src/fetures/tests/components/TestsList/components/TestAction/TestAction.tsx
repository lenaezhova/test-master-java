import React from 'react';
import s from './TestAction.module.scss';
import {Button} from 'antd';
import {DeleteOutlined} from '@ant-design/icons';
import {useCreateTest, useDeleteTest} from "../../../../../../api/tests/query";

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
      <Button
        loading={isLoading}
        icon={<DeleteOutlined/>}
        type={'text'}
        danger
        onClick={handleDeleteTest}
      />
    </div>
  );
};

export default TestAction;
