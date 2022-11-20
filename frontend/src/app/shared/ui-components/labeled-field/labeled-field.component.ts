import { Component, Input, Optional, Self } from '@angular/core';
import { ControlValueAccessor, NgControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { FormUtil } from '../../utils/form.utils';

@Component({
  selector: 'app-labeled-field',
  templateUrl: './labeled-field.component.html',
  styleUrls: ['./labeled-field.component.scss', '../../../../styles/form.scss']
  /*, providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi:true,
      useExisting: LabeledFieldComponent
    }
  ] <-- CANT BE USED WITH CONSTRUCTOR AS WELL*/
})
export class LabeledFieldComponent implements ControlValueAccessor  {

  @Input()
  type: 'text' 
      | 'number'
      | 'password' 
      | 'autocomplete'
      = 'text';
  
  @Input()
  labelKey: string = '';

  @Input()
  inputId: string = '';

  @Input()
  placeholder: string = '';
  
  @Input()
  required: boolean = true;

  inputValue: string = '';
  touched: boolean = false;

  onChangeCallback = (input: any) => {};
  onTouchedCallback = () => {};

  public writeValue(text: string): void {
      this.inputValue = text;
  }

  public registerOnChange(fn: any): void {
      this.onChangeCallback = fn;
  }

  public registerOnTouched(fn: any): void {
      this.onTouchedCallback = fn;
  }

  markAsTouched() {
    if(!this.touched) {
      this.touched = true;
      this.onTouchedCallback();
    }
  }

  constructor (@Self() @Optional() public control: NgControl
              , public translate : TranslateService) {
    this.control && (this.control.valueAccessor = this);
   }


   public get invalid(): boolean {
    return this.control ? this.control.invalid! : false;
   }

   public shouldShowError(): boolean {
    const { dirty, touched } = this.control;
    
    return (this.invalid) ? (dirty && touched)! : false;
   }

   public get error(): string {
    return this.translate.instant(FormUtil.errorsFromControl(this.control));
   }

}
