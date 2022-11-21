import { Injectable } from "@angular/core";
import { Select, Store } from "@ngxs/store";
import { Observable } from "rxjs";
import { Country } from "../shared/models/country";
import { FetchCountryList } from "./app-store.actions";
import { AppStateModel } from "./app-store.model";
import { AppState } from "./app-store.state";

@Injectable()
export class AppStoreService {
    constructor(private store : Store) { }

    @Select(AppState.countryList) public readonly countryList?: Observable<Country[]>;

    public get currentAppStoreSnapshot(): AppStateModel {
        return this.store.selectSnapshot(AppState);
    }

    public getCountriesByQuery(query: string) {
        var currentSnapshot = this.currentAppStoreSnapshot;
        return currentSnapshot.countryList.filter((country: Country) => country.name.toLowerCase().includes(query.toLowerCase()));
    } 

    public fetchCountryList() {
        this.store.dispatch(new FetchCountryList());
    }
}