import { City } from "../shared/models/city";
import { Country } from "../shared/models/country";
import { Message } from "../shared/models/message";


export interface AppStateModel {
    countryList: Country[],
    cityList: City[],
    messageList: Message[]
}


export const defaultAppState: AppStateModel = {
    countryList: [],
    cityList: [],
    messageList: []
};