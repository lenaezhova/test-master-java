import Tests from "../pages/Tests/Tests";
import {ComponentType} from "react";

export interface IRoute {
    path: string;
    component: ComponentType;
}

export enum RouteNames {
    TESTS = '/',
}

export const publicRoutes : IRoute[] = [
    {
        path: RouteNames.TESTS,
        component: Tests
    }
]

export const privateRoutes : IRoute[] = []
