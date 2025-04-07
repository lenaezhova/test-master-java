import {AnyObject} from "antd/es/_util/type";

export const API_URL_BASE = process.env.REACT_APP_API_URL
export const API_URL = process.env.REACT_APP_API_URL +  '/api'
export const CLIENT_URL= process.env.REACT_APP_CLIENT_URL

export const BIG_STALE_TIME = 120000;
export const EMPTY_OBJECT: AnyObject = {};
export const requiredMessage = 'Заполните поле'
