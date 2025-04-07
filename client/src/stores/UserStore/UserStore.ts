import {jwtDecode} from 'jwt-decode';
import {action, computed, makeObservable, observable} from 'mobx';
import {BaseStore} from '../BaseStore';
import {getUser, logout, updateRefreshToken} from "../../api/user";
import {message} from "antd";
import {UserTokensStore} from "./UserTokensStore";
import {AccessToken, IUser, JwtTokenPair} from "../../api/user/type";

class UserStore extends UserTokensStore {
  @observable isAuth = false;

  constructor() {
    super();
    makeObservable(this);
    this.fetchItem = (params?) => {
      return super.fetchItem({
        id: this.decodedAccessToken?.id,
        ...params
      });
    }
  }

  fetchItemMethod = getUser;

  fetchItemSuccess = response => {
    this.item = response;
  }

  @action setIsAuth(isAuth: boolean) {
    this.isAuth = isAuth;
  }

  @computed get isConfirmEmail(): Boolean {
    return Boolean(this.isAuth && this.item?.isActivate && this.item?.activationLink)
  }

  @computed get firstNameLetter(): string {
    return this.item.name.slice(0, 1);
  }

  @action
  async checkAuth() {
    try {
      const response = await updateRefreshToken({ refreshToken: this.refreshToken });
      const accessToken = response.accessToken;
      this.setAccessToken(accessToken);
      this.setRefreshToken(response.refreshToken);
      this.setIsAuth(true);
    } catch (e) {
      this.setIsAuth(false);
      this.resetItem();
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
    }
  }

  @action login(tokens: JwtTokenPair) {
    this.setAccessToken(tokens.accessToken);
    this.setRefreshToken(tokens.refreshToken);
    this.setIsAuth(true);
  }
}

export {UserStore};
