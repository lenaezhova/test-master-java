import {useNavigate} from 'react-router-dom'
import {Button, Layout} from "antd";
import {AllBaseStores, injectBase} from "../../stores/stores";
import {observer} from "mobx-react";
import {FullscreenExitOutlined, LogoutOutlined} from "@ant-design/icons";

const Navbar = injectBase(['$user'])(observer((props: AllBaseStores) => {
  const { $user } = props;
  const navigate = useNavigate();

  const handleExit = async () => {
    try {
      await $user.logout();
    } catch (e) {
      console.log(e)
    }
  }

  return (
    <Layout.Header style={{padding: '0'}}>
      <div className={"navbar container"}>
        <button className={'logo clearButton white'} onClick={() => navigate('/')}>
          Test Master
        </button>
        {$user.isAuth && (
          <Button type={'text'} danger onClick={handleExit} icon={<LogoutOutlined/>} />
        )}
      </div>
    </Layout.Header>
  );
}));

export default Navbar;
