import { HttpErrorResponse } from "@angular/common/http";
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