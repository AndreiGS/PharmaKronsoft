import { Injectable } from '@angular/core';
import { delay, Observable, of } from 'rxjs';
import { CityMock } from 'src/test/mocks/city.mock';
import { CountryMock } from 'src/test/mocks/country.mock';
import { City } from '../models/city';
import { Country } from '../models/country';


@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor() { }

  public getCountries(): Observable<Country[]> {
    return of(CountryMock.countries).pipe(delay(200))
  }

  public getCities(): Observable<City[]> {
    return of(CityMock.cities).pipe(delay(200));
  }

  public getCitiesByCountryId(country_id: number): Observable<City[]> {
    return of(
      CityMock.cities.filter((city: City) => city.country_id = country_id)
    ).pipe(delay(200));
  }
}
