export interface UserLoginDTO {
    username: string,
    password: string
}

export interface UserLoginResponseDTO {
    username: string,
    jwtToken: string,
    refreshToken: string
}