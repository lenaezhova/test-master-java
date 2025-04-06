import {jwtDecode} from 'jwt-decode';
import {action, computed, makeObservable, observable} from 'mobx';
import {BaseStore} from '../BaseStore';
import {getUser, logout, updateRefreshToken} from "../../api/user";
import {API_URL} from "../../api";
import {message} from "antd";
import {UserTokensStore} from "./UserTokensStore";
import {IUser, JwtTokenPair} from "../../api/user/type";
import {EMPTY_OBJECT} from "../../utils/const";

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

  @computed get currentUser(): IUser {
    const token = this.accessToken;
    try {
      return token ? jwtDecode(token) : EMPTY_OBJECT
    } catch (error) {
      console.log(error);
      return EMPTY_OBJECT;
    }
  }

  @computed get currentUserNameLetter(): string {
    return this.currentUser.name.slice(0, 1);
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
    } finally {
      this.setIsAuthLoading(false);
    }
  }

  @action
  async logout() {
    try {
      await logout({refreshToken: this.refreshToken});
      this.removeAccessToken();
      this.removeRefreshToken();
      this.setIsAuth(false);
    } catch (e) {
      message.error('Произошла ошибка')
    } finally {
      this.setIsAuthLoading(false);
    }
  }

  @action login(tokens: JwtTokenPair) {
    this.setAccessToken(tokens.accessToken);
    this.setRefreshToken(tokens.refreshToken);
    this.setIsAuth(true);
  }
}

export {UserStore};
