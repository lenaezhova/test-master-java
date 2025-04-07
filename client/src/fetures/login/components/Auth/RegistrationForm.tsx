import {FC, memo} from "react";
import {Button, Form, FormProps, Input, Row} from "antd";
import {useForm} from "antd/lib/form/Form";
import {requiredMessage} from "../../../../utils/const";
import s from './Auth.module.scss'
import {Link} from "react-router";
import {RouteNames} from "../../../../shared/router";
import {useRegistration} from "../../../../api/user/query";
import {CreateUserRequest} from "../../../../api/user/type";
import {AllBaseStores, injectBase} from "../../../../stores/stores";
import {observer} from "mobx-react";

type FieldType = CreateUserRequest & {
  confirm: string;
};

interface RegistrationFormProps extends AllBaseStores {}

const RegistrationForm: FC<RegistrationFormProps> = injectBase(['$user'])(observer((props) => {
  const { $user } = props;
  const registration = useRegistration();
  const isLoading = registration.isLoading || $user.fetchItemProgress;

  const [form] = useForm();

  const onSendForm: FormProps<FieldType>['onFinish'] = async (values) => {
    try {
      const response = await registration.mutateAsync(values);
      $user.login(response.data);
      await $user.fetchItem();
    } catch (e) {
      console.log(e);
    }
  }

  return (
    <div className={s.auth}>
      <h1>Регистрация</h1>
      <Form
        form={form}
        layout={'vertical'}
        onFinish={onSendForm}
      >
        <Form.Item<FieldType>
          name={'name'}
          rules={[{
            required: true,
            message: requiredMessage
          }]}
        >
          <Input placeholder={'Имя'} />
        </Form.Item>
        <Form.Item<FieldType>
          name={'email'}
          rules={[{
            type: 'email',
            message: 'Некорректный email'
          }]}
        >
          <Input placeholder={'Email'} />
        </Form.Item>

        <Form.Item<FieldType>
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

        <Form.Item<FieldType>
          name="confirm"
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
          <Input.Password placeholder={'Повторите пароль'} />
        </Form.Item>

        <Form.Item>
          <Row justify='space-between' align='middle'>
            <div className={s.footerLinkWrapper}>
              <span>Есть аккаунт?</span>
              <Link to={RouteNames.LOGIN}>Войдите!</Link>
            </div>
            <Button
              loading={isLoading}
              htmlType={'submit'}
              type={'primary'}
            >
              Регистрация
            </Button>
          </Row>
        </Form.Item>

      </Form>
    </div>
  );
}));

export default RegistrationForm;
