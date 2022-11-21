import { Injectable } from '@angular/core';
import { delay, Observable, of } from 'rxjs';
import { Country } from '../models/country';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  constructor() { }

  private dummyCountries: Country[] = [
      { id: 1, name: 'Afghanistan' }
    , { id: 2, name: 'Albania' }
    , { id: 3, name: 'Algeria' }
    , { id: 4, name: 'American Samoa' }
    , { id: 5, name: 'Andorra' }
    , { id: 6, name: 'Angola' }
    , { id: 7, name: 'Belarus' }
    , { id: 8, name: 'Belgium' }
    , { id: 9, name: 'Bulgaria' }
    , { id:10, name: 'China' }
    , { id:11, name: 'Romania' }
    , { id:12, name: 'United States' }
  ];

  public getCountries(): Observable<Country[]> {
    return of(this.dummyCountries).pipe(delay(200))
  }
}
