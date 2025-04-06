import {FC, memo} from "react";
import {Button, Form, FormProps, Input, Row} from "antd";
import {useForm} from "antd/lib/form/Form";
import s from './Auth.module.scss'
import {requiredMessage} from "../../../utils/const";
import {Link} from "react-router";
import {RouteNames} from "../../../shared/router";
import {CreateUserRequest, LoginRequest} from "../../../api/user/type";
import {useLogin} from "../../../api/user/query";
import {AllBaseStores, injectBase} from "../../../stores/stores";
import {observer} from "mobx-react";

type FieldType = LoginRequest

interface LoginFormProps extends AllBaseStores {}

const LoginForm: FC<LoginFormProps> = injectBase(['$user'])(observer( (props) => {
  const { $user } = props;

  const login = useLogin();

  const [form] = useForm();

  const onSendForm: FormProps<FieldType>['onFinish'] = async (values) => {
    try {
      const response = await login.mutateAsync(values);
      $user.login(response?.data);
    } catch (e) {
      console.log(e);
    }
  }

  return (
    <div className={s.auth}>
      <h1>Авторизация</h1>
      <Form
        form={form}
        layout={'vertical'}
        onFinish={onSendForm}
      >
        <Form.Item
          name={'email'}
          rules={[{
            type: 'email',
            message: 'Некорректный email'
          }]}
        >
          <Input placeholder={'Email'} />
        </Form.Item>

        <Form.Item
          name="password"
          rules={[
            {
              required: true,
              message: requiredMessage,
            },
          ]}
          hasFeedback
        >
          <Input.Password placeholder={'Пароль'} />
        </Form.Item>

        <Form.Item>
          <Row justify='space-between' align='middle'>
            <div className={s.footerLinkWrapper}>
              <span>Нет аккаунта?</span>
              <Link to={RouteNames.REGISTRATION}>Зарегистрируйтесь!</Link>
            </div>
            <Button htmlType={'submit'} type={'primary'}>Войти</Button>
          </Row>
        </Form.Item>

      </Form>
    </div>
  );
}));

export default LoginForm;
