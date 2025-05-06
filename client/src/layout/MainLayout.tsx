'use client'
import {FC, memo, useEffect, useLayoutEffect, useState} from "react";
import {AllBaseStores, injectBase} from "../stores/stores";
import {observer} from "mobx-react";
import {Outlet} from "react-router";
import {Layout, Row, Spin} from "antd";
import Navbar from "../shared/ui/Navbar/Navbar";

const MainLayout: FC<AllBaseStores> = injectBase(['$user'])(observer(props => {
  const { $user } = props;
  const [isAuthLoading, setIsAuthLoading] = useState(true);

  const fetch = async () => {
    try {
      await $user.checkAuth();
      await $user.fetchItem();
    } catch (e) {
      console.log(e)
    } finally {
      setIsAuthLoading(false)
    }
  }

  useEffect(() => {
    if ($user.accessToken) {
      fetch();
    } else {
      setIsAuthLoading(false);
    }
  }, [$user.accessToken]);

  return (
    isAuthLoading || $user.fetchItemProgress
      ? (
        <Row justify={'center'} align={'middle'} style={{ height: '100vh' }}>
          <Spin spinning />
        </Row>
      )
      : (
        <Layout>
          <Navbar/>
          <Layout.Content>
            <Outlet />
          </Layout.Content>
        </Layout>
      )

  );
}));

export default MainLayout;
