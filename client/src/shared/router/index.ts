import Tests from "../../pages/Tests/Tests";
import {ComponentType} from "react";
import Login from "../../pages/Login/Login";

export interface IRoute {
    path: string;
    component: ComponentType;
}

export interface IRoutesModel {
    redirect: string;
    routes: IRoute[]
}

export enum RouteNames {
    LOGIN = '/login',
    REGISTRATION = '/registration',
    TESTS = '/',
}
