import { AuthService } from 'src/app/shared/services/auth.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss'],
})
export class NavigationComponent {
  constructor(public authService: AuthService) {}
}
