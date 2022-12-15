import {
  AbstractControl,
  AsyncValidatorFn,
  FormGroup,
  ValidatorFn,
  Validators,
} from '@angular/forms';
import { map } from 'rxjs';
import { UserService } from '../services/user.service';

export namespace CustomValidators {
  export namespace RegexPattern {
    export const username = /^[a-zA-Z][a-zA-Z0-9]*$/;
    export const password = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])^.+$/;
  }

  /* messageKey relative to errors.field (e.g. pattern.username, pattern.password, ...) */
  export function PatternValidator(
    pattern: string | RegExp,
    messageKey: string
  ): ValidatorFn {
    const delegateFn = Validators.pattern(pattern);
    return (control) =>
      delegateFn(control) === null ? null : { pattern: messageKey };
  }

  export function UniqueUsernameValidator(
    userService: UserService
  ): AsyncValidatorFn {
    return (control: AbstractControl) => {
      return userService
        .checkIfUsernameExists(control.value)
        .pipe(map((result: boolean) => (result ? { unique: false } : null)));
    };
  }

  /* In order to function correctly, dont forget to updateValueAndValidity of control when matchToControl its value changes. */
  export function MatchValidator(
    matchTo: string,
    errorKey: string
  ): ValidatorFn {
    return (control: AbstractControl) => {
      if (control == null || control.parent == null) return null;
      if (!(control.parent instanceof FormGroup)) return null;
      var parent = control.parent as FormGroup;
      var matchToControl = parent.controls[matchTo];
      return control.value == matchToControl.value
        ? null
        : { matchfail: errorKey };
    };
  }
}
