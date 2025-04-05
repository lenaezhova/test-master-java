import {jwtDecode} from 'jwt-decode';
import {action, computed, observable} from 'mobx';
import {BaseStore} from '../BaseStore';
import {getUser, updateRefreshToken} from "../../api/user";
import {API_URL} from "../../api";
import {message} from "antd";

class UserTokensStore extends BaseStore {
    @computed get accessToken() {
        return localStorage.getItem('access-token')
    }

    @computed get refreshToken() {
        return localStorage.getItem('refresh-token')
    }

    @action setAccessToken(token: string){
        localStorage.setItem('access-token', token);
    }

    @action setRefreshToken(token: string){
        localStorage.setItem('refresh-token', token);
    }
}

export {UserTokensStore};
