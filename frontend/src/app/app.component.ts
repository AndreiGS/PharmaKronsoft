import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ArticleStoreService } from './modules/article/store/article-store.service';
import { AppStoreService } from './store/app-store.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(public translate: TranslateService
              , public appStoreService: AppStoreService
              , public loadingProcessStoreService: ArticleStoreService) {
    translate.addLangs(['en']);
  }

  ngOnInit() {
    this.appStoreService.fetchCountryList();
    this.appStoreService.fetchCityList();
    this.loadingProcessStoreService.fetchLoadingProcess();
  }
}
