import { HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Action, Selector, State, StateContext } from "@ngxs/store";
import { catchError, tap } from "rxjs";
import { Country } from "../shared/models/country";
import { CountryService } from "../shared/services/country.service";
import { FetchCountryList, FetchCountryListCompleted, FetchCountryListFailed } from "./app-store.actions";
import { AppStateModel, defaultAppState } from "./app-store.model";

@State<AppStateModel>({
    name: 'appState',
    defaults: defaultAppState
})
@Injectable()
export class AppState {
    constructor(private countryService: CountryService) { }

    @Selector()
    public static countryList(state: AppStateModel): Country[] {
        return state.countryList;
    }

    @Action(FetchCountryList)
    public fetchCountryList(ctx : StateContext<AppStateModel>, action: FetchCountryList) {
        return this.countryService.getCountries().pipe(
            tap((response: Country[]) => {
                ctx.dispatch(new FetchCountryListCompleted(response));
            }),
            catchError((error: HttpErrorResponse) => {
                return ctx.dispatch(new FetchCountryListFailed(error));
            })
        );
    }

    @Action(FetchCountryListCompleted)
    public fetchCountryListCompleted(ctx: StateContext<AppStateModel>, action: FetchCountryListCompleted) {
        ctx.setState({
            ...ctx.getState(),
            countryList: action.countries
        });
    }

    @Action(FetchCountryListFailed)
    public fetchCountryListFailed(ctx: StateContext<AppStateModel>, action: FetchCountryListFailed) {
        console.log(action.error);
    }
}