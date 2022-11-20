import { FormGroup, NgControl, ValidationErrors } from "@angular/forms";

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

    public static errorsFromControl(control: NgControl) {
        const errors: ValidationErrors | null = control.errors;
        if(errors == null)
            return '';
        var errorsArray = Object.keys(errors);
        const translateKey = 'errors.field.' + errorsArray[0];
        return translateKey;
    }
}