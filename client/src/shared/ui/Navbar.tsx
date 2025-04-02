import {useNavigate} from 'react-router-dom'

const Navbar = () => {
  const navigate = useNavigate();

  return (
    <div className="navbar">
      <div className={"container"}>
        <div style={{
          display: "flex",
          justifyContent: "space-between"
        }}>
          <button className={'clearButton'} onClick={() => navigate('/')}>
            Тесты
          </button>
        </div>
      </div>
    </div>
  );
};

export default Navbar;
