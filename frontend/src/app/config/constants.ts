export class Constants {
  public static readonly API: string = 'http://localhost:8080';
  public static readonly AUTH_BASE = '/auth';
  public static readonly AUTH_LOGIN_API: string =
    Constants.API + Constants.AUTH_BASE + '/login';
  public static readonly AUTH_REGISTER_API: string =
    Constants.API + Constants.AUTH_BASE + '/register';
  public static readonly USER_BASE = '/user';
  public static readonly USER_USERNAME_EXISTS_API: string =
    Constants.API + Constants.USER_BASE + '/is_username_unique';
}
