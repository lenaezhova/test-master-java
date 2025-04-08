export enum UserRoles {
    USER = 'Пользователь',
    AUTHOR = 'Автор',
    ADMIN = 'Администратор',
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

export interface TokenResponse {
    accessToken: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface CreateUserRequest {
    email: string;
    password: string;
    name: string;
}
