import React from 'react';
import {AllBaseStores, injectBase} from "../../../stores/stores";
import {observer} from "mobx-react";
import Avatar from "antd/lib/avatar/Avatar";
import {Button, Dropdown} from "antd";
import s from './NavBar.module.scss'
import clsx from "clsx";

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
            <div className={clsx("dropdown", s.userDropdown)}>
              <div className="dropdown-header-wrapper">
                <Avatar>{$user.firstNameLetter}</Avatar>
                <p>{$user.item.email}</p>
              </div>
              <div className={clsx("dropdown-item-wrapper", s.rolesWrapperItem)}>
                <div className={s.roles}>
                  <span>Роли: </span>
                  {$user.item.roles.map((role, index) => (
                    <div key={index} className={s.role}>
                      {role}
                    </div>
                  ))}
                </div>
              </div>
              <div className="dropdown-item-wrapper pointer" onClick={handleExit}>
                Выйти
              </div>
            </div>
          )
        }}
        trigger = {['click']}
      >
        < Avatar
          className={"pointer"}> {$user.firstNameLetter}</Avatar>
      </Dropdown>
    </div>
  );
}));

export default NavbarUser;
