export class Constants {

    public static readonly API: string = 'http://localhost:8080/api/v1';
    public static readonly AUTH_LOGIN_API: string = Constants.API + '/login';
    public static readonly AUTH_REGISTER_API: string = Constants.API + '/register';

    public static readonly ARTICLE_IMPORT_FILE_API: string = Constants.API + '/article/import';
    public static readonly ARTICLE_GET_ALL_API: string = Constants.API + '/article/all';
}