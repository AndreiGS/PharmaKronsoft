export class Constants {
  public static readonly API: string = 'http://localhost:8080/api/v1';
  public static readonly AUTH_BASE = '/auth';
  public static readonly AUTH_LOGIN_API: string =
    Constants.API + Constants.AUTH_BASE + '/login';
  public static readonly AUTH_REGISTER_API: string =
    Constants.API + Constants.AUTH_BASE + '/register';
  public static readonly USER_BASE = '/user';
  public static readonly USER_USERNAME_EXISTS_API: string =
    Constants.API + Constants.USER_BASE + '/username_exists';

  public static readonly REFRESH_HEADER = 'X-REFRESH-TOKEN';
  public static readonly JWT_HEADER = 'X-JWT-TOKEN';
}
