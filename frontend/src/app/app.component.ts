import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AppStoreService } from './store/app-store.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'frontend';

  constructor(public translate: TranslateService
              , public appStoreService: AppStoreService) {
    translate.addLangs(['en']);
  }

  ngOnInit() {
    this.appStoreService.fetchCountryList();
    this.appStoreService.fetchCityList();
  }
}
