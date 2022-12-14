import { FormGroup, NgControl, ValidationErrors } from "@angular/forms";
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


    public static invalidControlButTouched(control: NgControl) {
        const { dirty, touched } = control;
        return (control.invalid) ? (dirty && touched)! : false;
    }

    /**
     * Function gets control as input, retreives the first error in object and returns its translated error.
     * @param control FormControl to get errors from
     * @param translate TranslateService
     * @returns Returns translated error string
     */
    public static errorsFromControl(control: NgControl, translate: TranslateService) {
        const errors: ValidationErrors | null = control.errors;
        if(errors == null)
            return '';
        var inspectedErrorKey = Object.keys(errors)[0];
        var translateKey = 'errors.field.'; /* ngx translate location where all field errors are found */
        
        switch(inspectedErrorKey) 
        {
            case 'minlength': /* minlength errors are retreived as {minlength: {requiredLength: number, currentLength: number}} */
                translateKey += inspectedErrorKey; /* translation for these are found in errors.field.minlength */
                return translate.instant(translateKey, { length: errors[inspectedErrorKey].requiredLength }); /* translation expects {{length}} param */
            case 'matchfail':
            case 'pattern': /* matchfail and pattern errors are retreived as { matchfail/pattern: translateKey (relative to errors.field) } */
                translateKey += errors[inspectedErrorKey]; 
                break;
            default: /* errors where value of error_message is not important (e.g. required, unique ...) ({error_key: error_message})*/
                translateKey += inspectedErrorKey;  
                break;
        }
        return translate.instant(translateKey);
    }
}
