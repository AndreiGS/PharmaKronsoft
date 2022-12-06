import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { delay, of } from 'rxjs';
import { Constants } from 'src/app/config/constants';
import { ArticleMock } from 'src/test/mocks/article.mock';
import { Article } from '../models/article';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(private httpClient : HttpClient) { }

  public getArticles() {
    return this.httpClient.get<Article[]>(Constants.ARTICLE_GET_ALL_API);
  }

  public importArticles(articles_json_file: any) {
    return this.httpClient.post<string>(Constants.ARTICLE_IMPORT_FILE_API, articles_json_file);
  }
}
