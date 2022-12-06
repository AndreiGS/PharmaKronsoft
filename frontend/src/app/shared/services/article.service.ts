import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { delay, of } from 'rxjs';
import { Constants } from 'src/app/config/constants';
import { ArticleMock } from 'src/test/mocks/article.mock';
import { Article } from '../models/article';
import { PageOf } from '../models/page-of';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private httpClient : HttpClient) { }

  public getArticles() {
    return this.httpClient.get<Article[]>(Constants.ARTICLE_GET_ALL_API);
  }

  public getPageOfArticles(pageNumber: number, itemsPerPage: number, sortBy: string, order: string) {
    return this.httpClient.get<PageOf<Article>>(Constants.ARTICLE_GET_PAGE_API, {
      params: {
        'page': pageNumber,
        'items': itemsPerPage,
        'sortBy': sortBy,
        'order': order
      }
    });
  }

  public importArticles(articles_json_file: any) {
    return this.httpClient.post<string>(Constants.ARTICLE_IMPORT_FILE_API, articles_json_file);
  }
}
