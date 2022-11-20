import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

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
    username: new FormControl(),
    password: new FormControl(),
    confirmPassword: new FormControl(),
    address: new FormControl(),
    city: new FormControl(),
    country: new FormControl()
  });



  ngOnInit(): void {
  }

  onClick() {
    var obj = {
      username: this.formData.controls.username.value
    };
    console.log(obj);
  }

}
