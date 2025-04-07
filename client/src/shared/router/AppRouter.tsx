import {FC, Fragment } from 'react';
import {Routes, Route, Navigate} from 'react-router-dom'
import {RouteNames} from "./index";
import {AllBaseStores, injectBase} from "../../stores/stores";
import {observer} from "mobx-react";
import MainLayout from "../../layout/MainLayout";
import Tests from "../../pages/Tests/Tests";
import {Layout} from "antd";
import Login from "../../pages/Login/Login";
import Registration from "../../pages/Registration/Registration";
import ConfirmEmail from "../../pages/ConfirmEmail/ConfirmEmail";
import ConfirmEmailLayout from "../../layout/ConfirmEmailLayout";

const AppRouter : FC = injectBase(['$user'])(observer((props:AllBaseStores) => {
  const { $user } = props;

  const getPublicRoutes = () => (
    <Fragment>
      <Route path={RouteNames.LOGIN} element={<Login />} />
      <Route path={RouteNames.REGISTRATION} element={<Registration />} />
      <Route path="*" element={<Navigate to={RouteNames.LOGIN} replace/>}/>
    </Fragment>
  )

  const getPrivateRoutes = () => (
    <Route element={<ConfirmEmailLayout/>}>
      <Route element={<Layout.Content />}>
      </Route>
      <Route path={RouteNames.TESTS}>
        <Route index element={<Tests />} />
      </Route>
      <Route path="*" element={<Navigate to={RouteNames.TESTS} replace/>}/>
    </Route>
  )

  return (
    <Routes>
      <Route element={<MainLayout />}>
        { $user.isAuth ? getPrivateRoutes() : getPublicRoutes() }
      </Route>
    </Routes>
  );
}));

export default AppRouter;
