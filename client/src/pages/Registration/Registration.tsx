import React from 'react';
import {Row} from "antd";
import RegistrationForm from "../../fetures/login/components/Auth/RegistrationForm";

const Registration = () => {
  return (
    <div className="container">
      <Row justify="center" align="middle" className="auth">
        <RegistrationForm />
      </Row>
    </div>
  );
};

export default Registration;
