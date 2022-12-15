import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';

import { FileUploadModule } from 'primeng/fileupload';
import { ProgressBarModule } from 'primeng/progressbar';
import { PaginatorModule } from 'primeng/paginator';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';
import {ProgressSpinnerModule} from 'primeng/progressspinner';


import { ArticleImportProgressComponent } from './components/article-import-progress/article-import-progress.component';
import { ArticleDisplayComponent } from './components/article-display/article-display.component';
import { ArticleService } from './services/article.service';
import { ArticleStoreService } from './store/article-store.service';

@NgModule({
  declarations: [
    ArticleDisplayComponent,
    ArticleImportProgressComponent
  ],
  imports: [
    CommonModule,
    TranslateModule,

    FileUploadModule,
    ProgressBarModule,
    PaginatorModule,
    TableModule,
    DialogModule,
    ProgressSpinnerModule
  ],
  exports: [
    ArticleDisplayComponent,
    ArticleImportProgressComponent
  ],
  providers: [ArticleService, ArticleStoreService]
})
export class ArticleModule { }
