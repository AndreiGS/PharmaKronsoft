import { City } from "../shared/models/city";
import { Country } from "../shared/models/country";


export interface AppStateModel {
    countryList: Country[],
    cityList: City[]
}


export const defaultAppState: AppStateModel = {
    countryList: [],
    cityList: []
};