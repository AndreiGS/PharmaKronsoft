import { Component, OnInit } from '@angular/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { TranslateService } from '@ngx-translate/core';
import { LazyLoadEvent } from 'primeng/api';
import { Article } from 'src/app/shared/models/article';
import { PageOf } from 'src/app/shared/models/page-of';
import { ArticleService } from 'src/app/shared/services/article.service';


@UntilDestroy()
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

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
              , public articleService: ArticleService) { }

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
    console.log(event.files);
    const formData = new FormData();
    formData.append('json-file', event.files[0]);

    this.articleService.importArticles(formData).pipe(untilDestroyed(this)).subscribe({
      next: (result: any) => {
        console.log(result);
        this.displayUploadDialog = false;
      },
      error: (error: any) => {
        console.log('error');
        console.log(error);
      }
    })
  }

  getArticles() {
    /*this.articleService.getArticles().pipe(untilDestroyed(this)).subscribe({
      next: (result: Article[]) => {
        this.articles = result;
      }
    });*/
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
    console.log(event.page);
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
