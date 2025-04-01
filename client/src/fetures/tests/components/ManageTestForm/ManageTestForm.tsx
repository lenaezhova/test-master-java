import React from 'react';
import s from './ManageTestForm.module.scss';
import {Button, Form, Input, Spin} from 'antd';
import {useForm} from 'antd/lib/form/Form';
import {useCreateTest, useTest, useUpdateTest} from '../../../../api/tests/query';

interface CreateTestFormProps {
  onSuccess?: () => void;
  onCancel?: () => void;
  id?: number;
}

const ManageTestForm = ({onCancel, onSuccess, id}: CreateTestFormProps) => {
  const isEdit = Boolean(id);
  const [form] = useForm();
  const {data: editTest} = useTest(id, {enabled: false});
  const createTest = useCreateTest();
  const updateTest = useUpdateTest();
  const isLoading = createTest.isLoading || updateTest.isLoading;

  const handleSendForm = async (data) => {
    try {
      await (isEdit ? updateTest : createTest).mutateAsync({ id,  ...data });
      onSuccess?.();
    } catch (e) {
      console.log(e)
    }
  };

  return (
    <Form
      form={form}
      onFinish={handleSendForm}
      initialValues={editTest}
      layout={'vertical'}
    >
      <Form.Item
        name={'title'}
        label={'Название'}
        required
      >
        <Input />
      </Form.Item>
      <Form.Item
        name={'description'}
        label={'Описание'}
      >
        <Input.TextArea />
      </Form.Item>
      <div className={s.btnWrapper}>
        <Button onClick={onCancel}>
          Закрыть
        </Button>
        <Spin spinning={isLoading}>
          <Button
            htmlType={'submit'}
            type={'primary'}
          >
            {isEdit ? 'Редактировать тест' : 'Создать тест'}
          </Button>
        </Spin>
      </div>
    </Form>
  );
};

export default ManageTestForm;
