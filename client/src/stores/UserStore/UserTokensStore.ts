import {jwtDecode} from 'jwt-decode';
import {action, computed, observable} from 'mobx';
import {BaseStore} from '../BaseStore';
import {getUser, updateRefreshToken} from "../../api/user";
import {API_URL} from "../../api";
import {message} from "antd";
import {
  getAccessToken,
  getRefreshToken,
  removeAccessToken,
  removeRefreshToken,
  setAccessToken,
  setRefreshToken
} from "../../utils/tokens";

class UserTokensStore extends BaseStore {
  @computed get accessToken() {
    return getAccessToken();
  }

  @computed get refreshToken() {
    return getRefreshToken()
  }

  @action removeAccessToken(){
    removeAccessToken();
  }

  @action removeRefreshToken(){
    removeRefreshToken();
  }

  @action setAccessToken(token: string){
    setAccessToken(token);
  }

  @action setRefreshToken(token: string){
    setRefreshToken(token);
  }
}

export {UserTokensStore};
