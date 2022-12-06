import { AuthService } from 'src/app/shared/services/auth.service';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { FormUtil } from 'src/app/shared/utils/form.utils';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss', '../../../styles/form.scss'],
})
export class LoginPageComponent {
  public formUtil = FormUtil;

  formData = new FormGroup({
    username: new FormControl(),
    password: new FormControl(),
  });

  constructor(
    public translate: TranslateService,
    public authService: AuthService
  ) {}

  onClick() {
    this.authService.login({
      username: this.formData.controls.username.value!,
      password: this.formData.controls.password.value!,
    });
  }
}
