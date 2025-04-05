import {FC, memo} from "react";
import {Form, Input} from "antd";
import {useForm} from "antd/lib/form/Form";
import {requiredMessage} from "../../../utils/const";
import s from './Auth.module.scss'

interface RegistrationFormProps {}

const RegistrationForm: FC<RegistrationFormProps> = (props) => {
  const [form] = useForm();
 
  return (
    <Form
      form={form}
      layout={'vertical'}
      className={s.auth}
    >
      <Form.Item
        name={'name'}
        label="Имя"
        rules={[{
          required: true,
          message: requiredMessage
        }]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        name={'email'}
        label="Email"
        rules={[{
          type: 'email',
          message: 'Некорректный email'
        }]}
      >
        <Input />
      </Form.Item>

      <Form.Item
        name="password"
        label="Пароль"
        rules={[
          {
            required: true,
            message: requiredMessage,
          },
        ]}
        hasFeedback
      >
        <Input.Password />
      </Form.Item>

      <Form.Item
        name="confirm"
        label="Повторите пароль"
        dependencies={['password']}
        hasFeedback
        rules={[
          {
            required: true,
            message: requiredMessage,
          },
          ({ getFieldValue }) => ({
            validator(_, value) {
              if (!value || getFieldValue('password') === value) {
                return Promise.resolve();
              }
              return Promise.reject(new Error('Пароли не совпадают!'));
            },
          }),
        ]}
      >
        <Input.Password />
      </Form.Item>

    </Form>
  );
};

export default RegistrationForm;
