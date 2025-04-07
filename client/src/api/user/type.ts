export enum UserRoles {
    USER = 'USER',
    AUTHOR = 'AUTHOR',
    ADMIN = 'ADMIN',
}


export interface IUser {
    id?: string;
    email?: string;
    name?: string;
    isActivate?: 'activate' | '';
    activationLink?: string;
    roles?: [UserRoles]
}

export type AccessToken = Pick<IUser, 'email' | 'name' | 'id'>

export interface JwtTokenPair {
    accessToken: string;
    refreshToken: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface LogoutRequest {
    refreshToken: JwtTokenPair['refreshToken'];
}

export interface CreateUserRequest {
    email: string;
    password: string;
    name: string;
}
