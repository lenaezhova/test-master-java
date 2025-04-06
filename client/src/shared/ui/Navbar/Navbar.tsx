import {useNavigate} from 'react-router-dom'
import {Button, Layout, Row} from "antd";
import {AllBaseStores, injectBase} from "../../../stores/stores";
import {observer} from "mobx-react";
import {FullscreenExitOutlined, LogoutOutlined, UserOutlined} from "@ant-design/icons";
import Avatar from "antd/lib/avatar/Avatar";
import NavbarUser from "./NavbarUser";

const Navbar = injectBase(['$user'])(observer((props: AllBaseStores) => {
  const {$user} = props;
  const navigate = useNavigate();

  return (
    <Layout.Header style={{padding: '0'}}>
      <div className={"navbar container"}>
        <button className={'logo clearButton white'} onClick={() => navigate('/')}>
          Test Master
        </button>
        {$user.isAuth && <NavbarUser/>}
      </div>
    </Layout.Header>
  );
}));

export default Navbar;
