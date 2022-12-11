import { Component, OnInit } from '@angular/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { createSubjectOnTheInstance } from '@ngneat/until-destroy/lib/internals';
import { TranslateService } from '@ngx-translate/core';
import { LazyLoadEvent } from 'primeng/api';
import { Article } from 'src/app/shared/models/article/article';
import { ArticleImportProcess } from 'src/app/shared/models/article/article-import-process';
import { PageOf } from 'src/app/shared/models/page-of';
import { ArticleService } from '../../services/article.service';
import { ArticleStoreService } from '../../store/article-store.service';

@UntilDestroy()
@Component({
  selector: 'app-article-display',
  templateUrl: './article-display.component.html',
  styleUrls: ['./article-display.component.scss']
})
export class ArticleDisplayComponent implements OnInit {

  loadedPage?: PageOf<Article>;
  columnsDisplayed: any[] = [];
  displayUploadDialog: boolean = false;
  tablePaginatorData: any = {
    pageNo: 0,
    rows: 6,
    sort: {
      field: 'id',
      order: 1
    }
  };

  constructor(public translate : TranslateService
              , public articleService: ArticleService
              , public articleStoreService: ArticleStoreService) { }

  ngOnInit(): void {
    this.columnsDisplayed = [
      { type: 'numeric', field: 'articleNo', header: 'article.entity.articleNo'}
      , { type: 'text', field: 'shortDesignation', header: 'article.entity.shortDesignation'}
      , { type: 'text', field: 'gtin', header: 'article.entity.gtin'}
      , { type: 'text', field: 'himiNr', header: 'article.entity.himiNr'}
      , { type: 'text', field: 'matchCode', header: 'article.entity.matchCode'}
      , { type: 'text', field: 'designation', header: 'article.entity.designation'}
      , { type: 'numeric', field: 'netPrice', header: 'article.entity.netPrice'}
    ];
  }

  openUploadDialog() {
    this.displayUploadDialog = true;
  }

  jsonUploader(event: any) {
    this.displayUploadDialog = false;
    
    const formData = new FormData();
    formData.append('file', event.files[0]);

    this.articleService.importArticles(formData).pipe(untilDestroyed(this)).subscribe({
      next: (result: ArticleImportProcess) => {
        console.log('Result from article service: ');
        this.articleStoreService.setLoadingProcess(result);
        this.articleStoreService.keepLoadingProcessUpdated();
        this.getArticles();
      },
      error: (error: any) => {
        console.log('error');
        console.log(error);
      }
    });
  }

  getArticles() {
    var stringOrder = (this.tablePaginatorData.order == 1) ? 'asc' : 'desc';
    this.articleService.getPageOfArticles(this.tablePaginatorData.pageNo
                                          , this.tablePaginatorData.rows
                                          , this.tablePaginatorData.sort.field
                                          , stringOrder).pipe(untilDestroyed(this)).subscribe({
      next: (result: PageOf<Article>) => {
        console.log(result);
        this.loadedPage = result;
      }
    });
  }

  onTablePageChange(event: any): void {
    this.tablePaginatorData.pageNo = event.page;
    this.getArticles();
  }

  customSort(event: LazyLoadEvent): void {
    if(event.sortField != null) {
      this.tablePaginatorData.sort.field = event.sortField;
      this.tablePaginatorData.sort.order = event.sortOrder;
    }
    this.getArticles();
  }

}
