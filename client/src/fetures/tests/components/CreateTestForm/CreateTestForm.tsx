import React from 'react';
import s from './CreateTestForm.module.scss';
import {Button, Form, Input} from 'antd';
import {useForm} from 'antd/lib/form/Form';
import {useCreateTest} from '../../../../api/tests/query';

const CreateTestForm = () => {
  const [form] = useForm();
  const {isLoading, mutateAsync} = useCreateTest();

  const handleSendForm = async (data) => {
    await mutateAsync(data);
  };

  return (
    <Form
      form={form}
      onFinish={handleSendForm}
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
        <Button
          htmlType={'submit'}
          type={'primary'}
          loading={isLoading}
        >
          Создать тест
        </Button>
      </div>
    </Form>
  );
};

export default CreateTestForm;
