import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/shared/services/user.service';
import { CustomValidators } from 'src/app/shared/utils/form.utils';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  formData = new FormGroup({
    firstName: new FormControl('', [ Validators.required ]),
    lastName: new FormControl('', [ Validators.required ]),
    username: new FormControl('', {
                    validators:  
                      [ Validators.required
                      , CustomValidators.PatternValidator(CustomValidators.RegexPattern.username, 'pattern.username')
                      , Validators.minLength(3) ],
                    asyncValidators: 
                      [ CustomValidators.UniqueUsernameValidator(this.userService) ],
            }),
    password: new FormControl('', 
                    [ Validators.required
                    , CustomValidators.PatternValidator(CustomValidators.RegexPattern.password,'pattern.password')
                    , Validators.minLength(5) ]),
    confirmPassword: new FormControl('', 
                    [ CustomValidators.MatchValidator('password', 'matchfail.password') ]),
    street: new FormControl('', [ Validators.required ]),
    city: new FormControl('', [ Validators.required ]),
    country: new FormControl('', [ Validators.required ]),
    agreeTermsOfService: new FormControl(false, [ Validators.required ])
  });

  constructor(public userService: UserService) {
    this.formData.controls.password.valueChanges.subscribe(() => {
      this.formData.controls.confirmPassword.updateValueAndValidity();
    });
   }


  ngOnInit(): void {
  }

  onClick() {
    console.log(this.formData);
  }

  public isFormDisabled() {
    return this.formData.invalid || this.formData.pending || !this.formData.controls['agreeTermsOfService'].value;
  }

}
