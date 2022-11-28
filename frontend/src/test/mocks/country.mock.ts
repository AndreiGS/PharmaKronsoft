import { Country } from "src/app/shared/models/country";

export class CountryMock {
    public static readonly countries: Country[] = [
        { id: 1, name: 'Afghanistan' }
        , { id: 2, name: 'Albania' }
        , { id: 3, name: 'Algeria' }
        , { id: 4, name: 'Andorra' }
        , { id: 5, name: 'Belarus' }
        , { id: 6, name: 'Bulgaria' }
        , { id: 7, name: 'China' }
        , { id: 8, name: 'Romania' }
        , { id: 9, name: 'United States' }
    ]
}