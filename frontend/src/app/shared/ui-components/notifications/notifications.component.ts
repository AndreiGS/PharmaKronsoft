import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AppStoreService } from 'src/app/store/app-store.service';
import { AppState } from 'src/app/store/app-store.state';
import { Message } from '../../models/message';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss'],
})
export class NotificationsComponent {
  //@Input() messages: Array<Message> = [];
  //@Output() onClick = new EventEmitter<Message>();

  @Select(AppState.messageList)
  messages?: Observable<Message[]>;

  constructor(public appStoreService: AppStoreService) {}

  deleteMessage(message: Message) {
    this.appStoreService.deleteMessage(message);
  }
}
