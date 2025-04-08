import {jwtDecode} from 'jwt-decode';
import {action, computed, observable} from 'mobx';
import {BaseStore} from '../BaseStore';
import {
  getAccessToken,
  removeAccessToken,
  setAccessToken
} from "../../utils/tokens";
import {AccessToken, IUser} from "../../api/user/type";
import {EMPTY_OBJECT} from "../../utils/const";

class UserTokensStore extends BaseStore<IUser> {
  @computed get accessToken() {
    return getAccessToken();
  }

  @computed get decodedAccessToken(): AccessToken {
    return this.accessToken ? jwtDecode(this.accessToken) : EMPTY_OBJECT as any;
  }

  @action removeAccessToken(){
    removeAccessToken();
  }

  @action setAccessToken(token: string){
    setAccessToken(token);
  }
}

export {UserTokensStore};
