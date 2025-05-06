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
  @computed get decodedAccessToken(): AccessToken {
    const accessToken = getAccessToken();
    return accessToken ? jwtDecode(accessToken) : EMPTY_OBJECT;
  }

  @action removeAccessToken(){
    removeAccessToken();
  }

  @action setAccessToken(token: string){
    setAccessToken(token);
  }
}

export {UserTokensStore};
