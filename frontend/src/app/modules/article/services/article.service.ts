import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { delay, of, retry, share, Subject, switchMap, takeUntil, timer } from 'rxjs';
import { Constants } from 'src/app/config/constants';
import { ArticleMock } from 'src/test/mocks/article.mock';
import { Article } from 'src/app/shared/models/article/article';
import { ArticleImportProcess } from 'src/app/shared/models/article/article-import-process';
import { PageOf } from 'src/app/shared/models/page-of';

@Injectable({
  providedIn: 'root'
})
export class ArticleService implements OnDestroy{

  constructor(private httpClient : HttpClient) { }
  private stopPolling = new Subject<void>();

  public getArticles() {
    return this.httpClient.get<Article[]>(Constants.ARTICLE_GET_ALL_API);
  }

  ngOnDestroy(): void {
      this.stopPolling.next();
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
    return this.httpClient.post<ArticleImportProcess>(Constants.ARTICLE_IMPORT_FILE_API, articles_json_file);
  }

  public poolArticleImportProcess(processId: string) {
    console.log('====== in pooling service ======');
    console.log(processId);
    return timer(10, 1600).pipe(
      switchMap(() => this.httpClient.get<ArticleImportProcess>(Constants.ARTICLE_GET_IMPORT_PROCESS_INFO_API, {params: {'processId':processId}})),
      retry(1),
      share(),
      takeUntil(this.stopPolling)
    );
  }

  public stopArticleImportProcessPolling() {
    this.stopPolling.next();
  }
  public fetchLoadingImportProcess() {
    return this.httpClient.get<ArticleImportProcess>(Constants.ARTICLE_GET_LOADING_IMPORT_PROCESS_API);
  }
}
