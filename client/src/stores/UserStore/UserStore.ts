import {action, computed, makeObservable, observable} from 'mobx';
import {getUser, logout, updateRefreshToken} from "../../api/user";
import {message} from "antd";
import {UserTokensStore} from "./UserTokensStore"
import {TokenResponse} from "../../api/user/type";

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
      const response = await updateRefreshToken();
      const accessToken = response.accessToken;
      this.setAccessToken(accessToken);
      this.setIsAuth(true);
    } catch (e) {
      this.setIsAuth(false);
      this.resetItem();
    }
  }

  @action
  async logout() {
    try {
      await logout();
      this.removeAccessToken();
      this.setIsAuth(false);
    } catch (e) {
      message.error('Произошла ошибка')
    }
  }

  @action login(tokens: TokenResponse) {
    this.setAccessToken(tokens.accessToken);
    this.setIsAuth(true);
  }
}

export {UserStore};
