export class Constants {
  public static readonly API = 'http://localhost:8080/api/v1';

  public static readonly AUTH_BASE = '/auth';
  public static readonly AUTH_LOGIN_API =
    Constants.API + Constants.AUTH_BASE + '/login';
  public static readonly AUTH_REGISTER_API =
    Constants.API + Constants.AUTH_BASE + '/register';
  public static readonly AUTH_LOGOUT_API =
    Constants.API + Constants.AUTH_BASE + '/logout';

  public static readonly USER_BASE = '/user';
  public static readonly USER_USERNAME_EXISTS_API =
    Constants.API + Constants.USER_BASE + '/username_exists';

  public static readonly ARTICLE: string = '/article';
  public static readonly ARTICLE_IMPORT_FILE_API: string =
    Constants.API + Constants.ARTICLE + '/import';
  public static readonly ARTICLE_GET_ALL_API: string =
    Constants.API + Constants.ARTICLE + '/all';
  public static readonly ARTICLE_GET_PAGE_API: string =
    Constants.API + Constants.ARTICLE + '/page';
  public static readonly ARTICLE_GET_IMPORT_PROCESS_INFO_API: string =
    Constants.API + Constants.ARTICLE + '/import-process-info';
  public static readonly ARTICLE_GET_LOADING_IMPORT_PROCESS_API: string =
    Constants.API + Constants.ARTICLE + '/loading-process';

  public static readonly NOTIFICATIONS_BASE = '/fcm';
  public static readonly DIRECT_NOTIFICATIONS_API =
    Constants.API + Constants.NOTIFICATIONS_BASE + '/notification';

  public static readonly REFRESH_HEADER = 'X-REFRESH-TOKEN';
  public static readonly JWT_HEADER = 'X-JWT-TOKEN';
}
