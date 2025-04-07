import React from 'react';
import {Row} from "antd";
import RegistrationForm from "../../fetures/login/components/Auth/RegistrationForm";
import LoginForm from "../../fetures/login/components/Auth/LoginForm";
import ConfirmEmailBlock from "../../fetures/login/components/ConfirmEmailBlock/ConfirmEmailBlock";

const ConfirmEmail = () => {
  return (
    <div className="container">
      <Row justify="center" align={'middle'} className="auth">
        <ConfirmEmailBlock />
      </Row>
    </div>
  );
};

export default ConfirmEmail;
