import { AuthService } from 'src/app/shared/services/auth.service';
import { Constants } from 'src/app/config/constants';
import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AppStoreService } from './store/app-store.service';
import { AngularFireMessaging } from '@angular/fire/compat/messaging';
import { HttpClient } from '@angular/common/http';
import { Message } from './shared/models/message';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'frontend';
  messages: Array<Message> = [];

  constructor(
    public translate: TranslateService,
    public appStoreService: AppStoreService,
    private msg: AngularFireMessaging,
    private http: HttpClient,
    private authService: AuthService
  ) {
    translate.addLangs(['en']);
  }

  ngOnInit() {
    this.appStoreService.fetchCountryList();
    this.appStoreService.fetchCityList();
    this.requestNotificationsToken();
  }

  requestNotificationsToken() {
    this.msg.requestToken.subscribe({
      next: (token) => {
        this.http
          .post(
            Constants.DIRECT_NOTIFICATIONS_API,
            {
              target: token,
              title: 'Hello world',
              message: 'First notification, kinda nervous',
            },
            {
              headers: {
                [Constants.JWT_HEADER]:
                  this.authService.getJwtAccessToken ?? '',
                [Constants.REFRESH_HEADER]:
                  this.authService.getRefreshToken ?? '',
              },
            }
          )
          .subscribe(() => {});
      },
      error: (error) => {
        console.error(error);
      },
    });

    this.msg.messages.subscribe((payload) => {
      // Get the data about the notification
      let notification = payload.notification;

      if (notification == null) {
        return;
      }
      // Create a Message object and add it to the array
      const message: Message = {
        title: notification.title ?? '',
        body: notification.body ?? '',
        iconUrl: notification.image,
      };
      this.messages.push(message);
      setTimeout(() => {
        this.deleteNotification(message);
      }, 3000);
    });
  }

  deleteNotification(message: Message) {
    this.messages = this.messages.filter((el) => el != message);
  }
}
