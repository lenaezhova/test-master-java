export interface IUser {
    email?: string;
    name?: string;
}

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
