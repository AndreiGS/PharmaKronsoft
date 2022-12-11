export class Constants {
    
    public static readonly API: string = 'http://localhost:8080/api/v1';
    public static readonly AUTH_LOGIN_API: string = Constants.API + '/login';
    public static readonly AUTH_REGISTER_API: string = Constants.API + '/auth/register';
    
    public static readonly ARTICLE: string = '/article';
    public static readonly ARTICLE_IMPORT_FILE_API: string = Constants.API + Constants.ARTICLE + '/import';
    public static readonly ARTICLE_GET_ALL_API: string = Constants.API + Constants.ARTICLE + '/all';
    public static readonly ARTICLE_GET_PAGE_API: string = Constants.API + Constants.ARTICLE + '/page';

    public static readonly ARTICLE_GET_IMPORT_PROCESS_INFO_API: string = Constants.API + Constants.ARTICLE + '/import-process-info'; 
    public static readonly ARTICLE_GET_LOADING_IMPORT_PROCESS_API: string = Constants.API + Constants.ARTICLE + '/loading-process'; 
}