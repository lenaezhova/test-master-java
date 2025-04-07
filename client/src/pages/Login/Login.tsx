import React from 'react';
import {Row} from "antd";
import RegistrationForm from "../../fetures/login/components/Auth/RegistrationForm";
import LoginForm from "../../fetures/login/components/Auth/LoginForm";

const Login = () => {
  return (
    <div className="container">
      <Row justify="center" align="middle" className="auth">
        <LoginForm />
      </Row>
    </div>
  );
};

export default Login;
