import { Injectable } from "@angular/core";
import { Select, Store } from "@ngxs/store";
import { Observable } from "rxjs";
import { City } from "../shared/models/city";
import { Country } from "../shared/models/country";
import { Message } from "../shared/models/message";
import { AddMessage, DeleteMessage, FetchCityList, FetchCountryList } from "./app-store.actions";
import { AppStateModel } from "./app-store.model";
import { AppState } from "./app-store.state";

@Injectable()
export class AppStoreService {
    constructor(private store : Store) { }

    @Select(AppState.countryList) public readonly countryList?: Observable<Country[]>;
    @Select(AppState.messageList) public readonly messageList?: Observable<Message[]>;

    public get currentAppStoreSnapshot(): AppStateModel {
        return this.store.selectSnapshot(AppState);
    }

    public getCountriesByQuery(query: string) {
        var currentSnapshot = this.currentAppStoreSnapshot;
        return currentSnapshot.countryList.filter((country: Country) => country.name.toLowerCase().includes(query.toLowerCase()));
    }

    public getCitiesByQuery(query: string) {
        var currentSnapshot = this.currentAppStoreSnapshot;
        return currentSnapshot.cityList.filter((city: City) => city.name.toLowerCase().includes(query.toLowerCase()));
    }

    public getCitiesByQueryAndCountry(query: string, country_id: number) {
        var currentSnapshot = this.currentAppStoreSnapshot;
        return currentSnapshot.cityList.filter((city: City) => city.name.toLowerCase().includes(query.toLowerCase())
                                                               && city.country_id == country_id);
    }

    public fetchCountryList() {
        this.store.dispatch(new FetchCountryList());
    }

    public fetchCityList() {
        this.store.dispatch(new FetchCityList());
    }

    public addMessage(message: Message) {
        this.store.dispatch(new AddMessage(message));
    }

    public deleteMessage(message: Message) {
        this.store.dispatch(new DeleteMessage(message));
    }
}