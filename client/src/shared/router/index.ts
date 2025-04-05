import Tests from "../../pages/Tests/Tests";
import {ComponentType} from "react";
import Login from "../../pages/Login/Login";

export interface IRoute {
    path: string;
    component: ComponentType;
}

export enum RouteNames {
    LOGIN = '/login',
    REGISTRATION = '/registration',
    TESTS = '/',
}

export const publicRoutes : IRoute[] = [
  {
    path: RouteNames.LOGIN,
    component: Login
  },
  {
    path: RouteNames.REGISTRATION,
    component: Login
  }
]

export const privateRoutes : IRoute[] = [
  {
    path: RouteNames.TESTS,
    component: Tests
  },
]
