import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Select, Selector } from '@ngxs/store';
import { Observable } from 'rxjs';
import { ArticleImportProcess } from 'src/app/shared/models/article/article-import-process';
import { ArticleStateModel } from '../../store/article-store.model';
import { ArticleState } from '../../store/article-store.state';

@Component({
  selector: 'app-article-import-progress',
  templateUrl: './article-import-progress.component.html',
  styleUrls: ['./article-import-progress.component.scss']
})
export class ArticleImportProgressComponent implements OnInit, OnChanges {

  
  constructor() { }

  @Select(ArticleState.loadingProcess)
  articleImportProcess!: Observable<ArticleImportProcess>;

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
  }

  computePercentage(processs: ArticleImportProcess) {
    return (processs.processedRecords * 100) / processs.totalRecords;
  }
}
