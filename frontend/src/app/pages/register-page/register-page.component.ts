import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { City } from 'src/app/shared/models/city';
import { Country } from 'src/app/shared/models/country';
import { AuthService } from 'src/app/shared/services/auth.service';
import { LocationService } from 'src/app/shared/services/location.service';
import { UserService } from 'src/app/shared/services/user.service';
import { CustomValidators } from 'src/app/shared/utils/validators.utils';
import { AppStoreService } from 'src/app/store/app-store.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.scss'],
})
export class RegisterPageComponent {
  formData = new FormGroup({
    firstName: new FormControl('', [Validators.required]),
    lastName: new FormControl('', [Validators.required]),
    username: new FormControl('', {
      validators: [
        Validators.required,
        CustomValidators.PatternValidator(
          CustomValidators.RegexPattern.username,
          'pattern.username'
        ),
        Validators.minLength(3),
      ],
      asyncValidators: [
        CustomValidators.UniqueUsernameValidator(this.userService),
      ],
    }),
    password: new FormControl('', [
      Validators.required,
      CustomValidators.PatternValidator(
        CustomValidators.RegexPattern.password,
        'pattern.password'
      ),
      Validators.minLength(5),
    ]),
    confirmPassword: new FormControl('', [
      CustomValidators.MatchValidator('password', 'matchfail.password'),
    ]),
    street: new FormControl('', [Validators.required]),
    city: new FormControl(
      {
        id: -1,
        country_id: -1,
        name: '',
      },
      [Validators.required]
    ),
    country: new FormControl(
      {
        id: -1,
        name: '',
      },
      [Validators.required]
    ),
    agreeTermsOfService: new FormControl(false, [Validators.requiredTrue]),
  });

  suggestedCountries: Country[] = [];
  suggestedCities: City[] = [];

  constructor(
    public userService: UserService,
    public authService: AuthService,
    public appStoreService: AppStoreService,
    public locationService: LocationService
  ) {
    this.formData.controls.country.setValue(null);
    this.formData.controls.city.setValue(null);

    this.formData.controls.password.valueChanges.subscribe(() => {
      this.formData.controls.confirmPassword.updateValueAndValidity();
    });
  }

  autocompleteCountryFx(query: string) {
    var countries: Country[] = this.appStoreService.getCountriesByQuery(query);
    this.suggestedCountries = countries;
  }

  autocompleteCityFx(query: string) {
    if (this.formData.controls.country.value) {
      var selectedCountry = this.formData.controls.country
        .value as unknown as Country;
      this.suggestedCities = this.appStoreService.getCitiesByQueryAndCountry(
        query,
        selectedCountry.id
      );
    } else {
      var cities: City[] = this.appStoreService.getCitiesByQuery(query);
      this.suggestedCities = cities;
    }
  }

  onCityAutocompleteSelect() {
    if (!this.formData.controls.country.value) {
      var selectedCity = this.formData.controls.city.value as unknown as City;
      var correspondentCountry =
        this.appStoreService.currentAppStoreSnapshot.countryList.find(
          (c: Country) => c.id == selectedCity.country_id
        );
      this.formData.controls.country.setValue(correspondentCountry!);
    }
  }

  onCountryAutocompleteSelect() {
    var cityValue = this.formData.controls.city.value as unknown as City;
    var countryValue = this.formData.controls.country
      .value as unknown as Country;
    if (cityValue && cityValue.country_id !== countryValue.id)
      this.formData.controls.city.setValue(null);
  }

  ngOnInit(): void {}

  onClick() {
    this.authService.register({
      username: this.formData.controls.username.value!,
      password: this.formData.controls.password.value!,
      confirmPassword: this.formData.controls.confirmPassword.value!,
      firstName: this.formData.controls.firstName.value!,
      lastName: this.formData.controls.lastName.value!,
      street: this.formData.controls.street.value!,
      city: this.formData.controls.city.value!,
      country: this.formData.controls.country.value!,
      acceptedTerms: this.formData.controls.agreeTermsOfService.value!,
    });
  }

  public isFormDisabled() {
    return this.formData.invalid || this.formData.pending;
  }
}
