import { Component, OnInit } from '@angular/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { createSubjectOnTheInstance } from '@ngneat/until-destroy/lib/internals';
import { TranslateService } from '@ngx-translate/core';
import { Select } from '@ngxs/store';
import { LazyLoadEvent } from 'primeng/api';
import { Observable } from 'rxjs';
import { Article } from 'src/app/shared/models/article/article';
import { ArticleImportProcess } from 'src/app/shared/models/article/article-import-process';
import { PageOf } from 'src/app/shared/models/page-of';
import { ArticleService } from '../../services/article.service';
import { ArticleStoreService } from '../../store/article-store.service';
import { ArticleState } from '../../store/article-store.state';

@UntilDestroy()
@Component({
  selector: 'app-article-display',
  templateUrl: './article-display.component.html',
  styleUrls: ['./article-display.component.scss']
})
export class ArticleDisplayComponent implements OnInit {

  @Select(ArticleState.loadingProcess)
  articleImportProcess!: Observable<ArticleImportProcess>;
  loadedPage?: PageOf<Article>;
  columnsDisplayed: any[] = [];
  displayUploadDialog: boolean = false;
  displayProgressSpinner: boolean = false;
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
      { type: 'numeric', field: 'id', header: 'article.entity.id'}
      , { type: 'text', field: 'pzn', header: 'article.entity.pzn'}
      , { type: 'text', field: 'produktname', header: 'article.entity.produktname'}
      , { type: 'text', field: 'kategorieName', header: 'article.entity.kategoriename'}
      , { type: 'text', field: 'price', header: 'article.entity.price'}
      , { type: 'text', field: 'availability', header: 'article.entity.availability'}
    ];
  }

  openUploadDialog() {
    this.displayUploadDialog = true;
  }

  jsonUploader(event: any, fileUpload: any) {
    this.displayUploadDialog = false;
    this.displayProgressSpinner = true;
    
    const formData = new FormData();
    formData.append('file', event.files[0]);
    this.articleService.importArticles(formData).pipe(untilDestroyed(this)).subscribe({
      next: (result: ArticleImportProcess) => {
        this.articleStoreService.setLoadingProcess(result);
        this.articleStoreService.keepLoadingProcessUpdated();
        this.getArticles();
      },
      error: (error: any) => {
        console.log('error');
        console.log(error);
      },
      complete: () => {
        this.displayProgressSpinner = false;
        fileUpload.clear();
      }
    });
  }

  getArticles() {
    var stringOrder = (this.tablePaginatorData.sort.order == 1) ? 'asc' : 'desc';
    console.log(stringOrder);
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
      console.log(this.tablePaginatorData);
    }
    this.getArticles();
  }

  onImportDialogHide(fileUpload: any) {
    fileUpload.clear();
  }

}
