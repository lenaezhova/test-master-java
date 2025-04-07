import React from 'react';
import {Outlet} from "react-router";
import {AllBaseStores, injectBase} from "../stores/stores";
import {observer} from "mobx-react";
import ConfirmEmailBlock from "../fetures/login/components/ConfirmEmailBlock/ConfirmEmailBlock";
import ConfirmEmail from "../pages/ConfirmEmail/ConfirmEmail";

const ConfirmEmailLayout = injectBase(['$user'])(observer( (props: AllBaseStores) => {
  const { $user } = props;

  return (
    $user.isConfirmEmail
      ? <Outlet />
      : <ConfirmEmail />
  );
}));

export default ConfirmEmailLayout;
