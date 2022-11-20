import { AbstractControl, FormControl, FormGroup, NgControl, ValidationErrors, ValidatorFn, Validators } from "@angular/forms";
import { TranslateService } from "@ngx-translate/core";

export class FormUtil {

    public static keyFrom(propertyNameStr: string, formGroup: FormGroup): any {
        type keyType = keyof typeof formGroup.controls; 
        var compatibleProp = propertyNameStr as keyType;
        return compatibleProp;
      }

    public static setDefaultValues(formGroup: FormGroup, source: any) {
        for(const key in formGroup.controls)
            formGroup.controls[key].setValue(source[key]);
    }

    public static getObjectFromFormValues(formGroup: FormGroup): any {
        var obj = {};
        for(const key in formGroup.controls)
            obj[key as keyof Object] = formGroup.controls[key].value;
        return obj;
    }

    public static invalidInputButTouched(propertyName: string, formGroup: FormGroup): boolean {
        var compatibleProp = this.keyFrom(propertyName, formGroup);
        return formGroup.controls[compatibleProp].invalid && 
            (formGroup.controls[compatibleProp].dirty || formGroup.controls[compatibleProp].touched);
    }

    public static invalidControlButTouched(control: NgControl) {
        const { dirty, touched } = control;
    
    return (control.invalid) ? (dirty && touched)! : false;
    }

    public static errorsFromControl(control: NgControl, translate: TranslateService) {
        const errors: ValidationErrors | null = control.errors;
        if(errors == null)
            return '';
        var errorsArray = Object.keys(errors);
        var translateKey = 'errors.field.';

        switch(errorsArray[0]) 
        {
            case 'minlength':
                translateKey += errorsArray[0];
                return translate.instant(translateKey, { length: errors[errorsArray[0]].requiredLength});
            case 'pattern':
                translateKey += errors[errorsArray[0]]; 
                break;
            default:
                translateKey += errorsArray[0];
                break;
        }
        return translate.instant(translateKey);
    }

    public static PatternValidator(pattern: string | RegExp, message: string): ValidatorFn {
        const delegateFn = Validators.pattern(pattern);
        console.log(message);
        return control => delegateFn(control) === null ? null : {pattern: message };
    }
}