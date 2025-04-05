import {FC, memo} from "react";
import {Form, Input, Row} from "antd";
import {useForm} from "antd/lib/form/Form";
import s from './Auth.module.scss'
import {requiredMessage} from "../../../utils/const";
import {Link} from "react-router";
import {RouteNames} from "../../../shared/router";

interface LoginFormProps {}

const LoginForm: FC<LoginFormProps> = (props) => {
  const [form] = useForm();

  return (
    <div className={s.auth}>
      <h1>Авторизация</h1>
      <Form
        form={form}
        layout={'vertical'}
      >
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

        <Row justify='space-between' align='middle' className={s.footer}>
          <div className={s.footerLeft}>
            <span>Нет аккаунта?</span>
            <Link to={RouteNames.REGISTRATION}>Зарегистрируйтесь!</Link>
          </div>
        </Row>
      </Form>
    </div>
  );
};

export default LoginForm;
