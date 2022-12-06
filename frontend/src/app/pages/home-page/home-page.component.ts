import { Component, OnInit } from '@angular/core';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { TranslateService } from '@ngx-translate/core';
import { PrimeNGConfig } from 'primeng/api';
import { Article } from 'src/app/shared/models/article';
import { ArticleService } from 'src/app/shared/services/article.service';
import { ArticleMock } from 'src/test/mocks/article.mock';


@UntilDestroy()
@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  articles: Article[] = [];
  columnsDisplayed: any[] = [];
  displayUploadDialog: boolean = false;

  constructor(public translate : TranslateService
              , public articleService: ArticleService) { }

  ngOnInit(): void {
    this.articleService.getArticles().pipe(untilDestroyed(this)).subscribe({
      next: (result: Article[]) => {
        this.articles = result;
      }
    });


    this.columnsDisplayed = [
      { field: 'articleNo', header: 'article.entity.articleNo'}
      , { field: 'shortDesignation', header: 'article.entity.shortDesignation'}
      , { field: 'gtin', header: 'article.entity.gtin'}
      , { field: 'himiNr', header: 'article.entity.himiNr'}
      , { field: 'matchCode', header: 'article.entity.matchCode'}
      , { field: 'designation', header: 'article.entity.designation'}
      , { field: 'netPrice', header: 'article.entity.netPrice'}
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
      },
      error: (error: any) => {
        console.log('error');
        console.log(error);
      }
    })
  }
}
