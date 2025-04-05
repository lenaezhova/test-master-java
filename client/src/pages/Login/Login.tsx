import React from 'react';
import {Row} from "antd";
import RegistrationForm from "../../fetures/login/components/RegistrationForm";
import LoginForm from "../../fetures/login/components/LoginForm";

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
