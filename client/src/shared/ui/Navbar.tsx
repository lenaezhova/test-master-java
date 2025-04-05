import {useNavigate} from 'react-router-dom'
import {Layout} from "antd";

const Navbar = () => {
  const navigate = useNavigate();

  return (
    <Layout.Header style={{padding: '0'}}>
      <div className={"navbar container"}>
        <button className={'clearButton white'} onClick={() => navigate('/')}>
          Test Master
        </button>
      </div>
    </Layout.Header>
  );
};

export default Navbar;
