import React from 'react';
import {AllBaseStores, injectBase} from "../../../stores/stores";
import {observer} from "mobx-react";
import Avatar from "antd/lib/avatar/Avatar";
import {LogoutOutlined, UserOutlined} from "@ant-design/icons";
import {Button, Dropdown} from "antd";

const NavbarUser = injectBase(['$user'])(observer((props: AllBaseStores) => {
  const { $user } = props;

  const handleExit = async () => {
    try {
      await $user.logout();
    } catch (e) {
      console.log(e)
    }
  }

  return (
    <div className={"avatar"}>
      <Dropdown
        placement={'bottomRight'}
        dropdownRender={(originNode) => {
          return (
            <div className={"dropdown"} style={{minWidth: 280}}>
              <div className="dropdown-header-wrapper">
                <Avatar>{$user.currentUserNameLetter}</Avatar>
                <p>{$user.currentUser.email}</p>
              </div>
              <div className="dropdown-item-wrapper" onClick={handleExit}>
                Выйти
              </div>
            </div>
          )
        }}
        trigger={['click']}
      >
        <Avatar className={"pointer"}>{$user.currentUserNameLetter}</Avatar>
      </Dropdown>
    </div>
  );
}));

export default NavbarUser;
