import { Country } from "../shared/models/country";


export interface AppStateModel {
    countryList: Country[];
}


export const defaultAppState: AppStateModel = {
    countryList: []
};