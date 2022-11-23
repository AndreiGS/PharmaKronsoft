import { Component, EventEmitter, Input, Optional, Output, Self } from '@angular/core';
import { ControlValueAccessor, NgControl } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';
import { Observable, isObservable, of } from 'rxjs';
import { FormUtil } from '../../utils/form.utils';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';


@UntilDestroy()
@Component({
  selector: 'app-labeled-field',
  templateUrl: './labeled-field.component.html',
  styleUrls: ['./labeled-field.component.scss', '../../../../styles/form.scss']
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

  @Input()
  keepLabelSpace: boolean = false;

  @Input()
  autocompleteSuggestions: string[] = [];

  @Input()
  forceAutocompleteSelection: boolean = true;

  @Output()
  autocompleteSearch = new EventEmitter<{ query: string }>();

  inputValue: string = '';

  touched: boolean = false;
  
  disabled: boolean = false;

  onChangeFn = (input: any) => {};
  
  onTouchedFn = () => {};

  constructor (@Self() @Optional() public control: NgControl
              , public translate : TranslateService) {
    this.control && (this.control.valueAccessor = this);
  }

  public writeValue(text: string): void {
      this.inputValue = text;
  }

  public registerOnChange(fn: any): void {
      this.onChangeFn = fn;
  }

  public registerOnTouched(fn: any): void {
      this.onTouchedFn = fn;
  }

  public setDisabledState(isDisabled: boolean): void {
      this.disabled = isDisabled;
  }
  
  public onBlur(): void {
    this.onTouchedFn();
  }
  
  public markAsTouched() {
    console.log('mark as touched');
    if(!this.touched) {
      this.touched = true;
      this.onTouchedFn();
    }
  }

  public onSelectAutocomplete(selectedString: string) {
    this.inputValue = selectedString;
    this.onChangeFn(selectedString);
  }

  public search(query: string) {
    this.autocompleteSearch.emit({ query : query });
  }

  public onclear() {
    console.log('clear');
  }

  public error(): string {
    return FormUtil.errorsFromControl(this.control, this.translate);
  }

  public get invalid(): boolean {
    return this.control ? this.control.invalid! : false;
  }

  public get invalidButTouched(): boolean {
    return FormUtil.invalidControlButTouched(this.control);
  }

  
}
