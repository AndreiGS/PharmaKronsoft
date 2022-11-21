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
    firstName: new FormControl(),
    lastName: new FormControl(),
    username: new FormControl('', {
                    validators:  
                      [ Validators.required
                      , CustomValidators.PatternValidator(/^[a-zA-Z][a-zA-Z0-9]*$/, 'pattern.username')
                      , Validators.minLength(3) ],
                    asyncValidators: 
                      [ CustomValidators.UniqueUsernameValidator(this.userService) ],
            }),
    password: new FormControl('', 
                    [ Validators.required
                    , CustomValidators.PatternValidator(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])^.+$/,'pattern.password')
                    , Validators.minLength(5)]),
    confirmPassword: new FormControl('', 
                    [CustomValidators.MatchValidator('password', 'matchfail.password')]),
    street: new FormControl(),
    city: new FormControl(),
    country: new FormControl(),
    checkbox: new FormControl()
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

}
