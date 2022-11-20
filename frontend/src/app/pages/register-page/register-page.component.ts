import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormUtil } from 'src/app/shared/utils/form.utils';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss']
})
export class RegisterPageComponent implements OnInit {

  constructor() { }

  formData = new FormGroup({
    firstName: new FormControl(),
    lastName: new FormControl(),
    username: new FormControl('', 
                    [ FormUtil.PatternValidator(/^[a-zA-Z][a-zA-Z0-9]*$/, 'pattern.username')
                    , Validators.minLength(3)]),
    password: new FormControl('', 
                    [ FormUtil.PatternValidator(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])^.+$/,'pattern.password')
                    , Validators.minLength(5)]),
    confirmPassword: new FormControl(''/*FormUtil.MatchValidator({matchTo: password}) */),
    street: new FormControl(),
    city: new FormControl(),
    country: new FormControl()
  });



  ngOnInit(): void {
  }

  onClick() {
    console.log(this.formData);
  }

}
