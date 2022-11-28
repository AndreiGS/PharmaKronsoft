import { HttpErrorResponse } from "@angular/common/http";
import { City } from "../shared/models/city";
import { Country } from "../shared/models/country";

export class FetchCountryList {
    static readonly type = '[AppState] Fetch CountryList';
    constructor() { }
}

export class FetchCountryListCompleted {
    static readonly type = '[AppState] Fetch CountryList completed.';
    constructor(public countries: Country[]) { }
}

export class FetchCountryListFailed {
    static readonly type = '[AppState] Fetch CountryList failed.';
    constructor(public error: HttpErrorResponse) { }
}

export class FetchCityList {
    static readonly type = '[AppState] Fetch CityList';
    constructor() { }
}

export class FetchCityListCompleted {
    static readonly type = '[AppState] Fetch CityList completed.';
    constructor(public cities: City[]) { }
}

export class FetchCityListFailed {
    static readonly type = '[AppState] Fetch CityList failed.';
    constructor(public error: HttpErrorResponse) { }
}