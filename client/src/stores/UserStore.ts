import {jwtDecode} from 'jwt-decode';
import {computed, observable} from 'mobx';
import {BaseStore} from './BaseStore';
import {getUser} from "../api/user";

class UserStore extends BaseStore {
  @observable isAuth = false;


  constructor() {
    super();
  }

  fetchItemMethod = getUser;

  fetchItemSuccess = response => {
    this.item = response;
  }

  @computed get currentUser() {
    const token = localStorage.getItem('access-token');

    try {
      return token ? jwtDecode(token) : {}
    } catch (error) {
      console.log(error);

      return {};
    }
  }
}

export {UserStore};
