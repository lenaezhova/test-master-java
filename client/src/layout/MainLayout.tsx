'use client'
import {FC, memo, useEffect} from "react";
import {AllBaseStores, injectBase} from "../stores/stores";
import {observer} from "mobx-react";
import {Outlet} from "react-router";
import {Layout, Spin} from "antd";
import Navbar from "../shared/ui/Navbar";

const MainLayout: FC<AllBaseStores> = injectBase(['$user'])(observer(props => {
  const { $user } = props;

  useEffect(() => {
    if ($user.accessToken) {
      $user.checkAuth()
    } else {
      $user.setIsAuthLoading(false);
    }
  }, []);

  return (
    <Spin spinning={$user.isAuthLoading}>
      <Layout>
        <Navbar/>
        <Outlet />
      </Layout>
    </Spin>
  );
}));

export default MainLayout;
