import {jwtDecode} from 'jwt-decode';
import {action, computed, makeObservable, observable} from 'mobx';
import {BaseStore} from '../BaseStore';
import {getUser, updateRefreshToken} from "../../api/user";
import {API_URL} from "../../api";
import {message} from "antd";
import {UserTokensStore} from "./UserTokensStore";

class UserStore extends UserTokensStore {
  @observable isAuthLoading = true;

  @observable isAuth = false;

  constructor() {
    super();
    makeObservable(this);
  }

  fetchItemMethod = getUser;

  fetchItemSuccess = response => {
    this.item = response;
  }

  @action setIsAuthLoading(isAuthLoading: boolean) {
    this.isAuthLoading = isAuthLoading;
  }

  @action setIsAuth(isAuth: boolean) {
    this.isAuth = isAuth;
    this.setIsAuthLoading(false);
  }

  @computed get currentUser() {
    const token = this.accessToken;
    try {
      return token ? jwtDecode(token) : {}
    } catch (error) {
      console.log(error);
      return {};
    }
  }

  @action
  async checkAuth() {
    this.setIsAuthLoading(true);
    try {
      const response = await updateRefreshToken({ refreshToken: this.refreshToken });
      this.setAccessToken(response.accessToken);
      this.setRefreshToken(response.refreshToken);
      this.setIsAuth(true);
    } catch (e) {
      this.setIsAuth(false);
      message.error('Произошла ошибка при проверка авторизации')
    } finally {
      this.setIsAuthLoading(false);
    }
  }
}

export {UserStore};
