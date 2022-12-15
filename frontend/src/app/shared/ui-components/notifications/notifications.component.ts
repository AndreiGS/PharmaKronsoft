import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Message } from '../../models/message';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss'],
})
export class NotificationsComponent {
  @Input() messages: Array<Message> = [];
  @Output() onClick = new EventEmitter<Message>();

  constructor() {}
}
