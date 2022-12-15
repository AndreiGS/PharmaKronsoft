import { HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Action, Selector, State, StateContext } from "@ngxs/store";
import { catchError, tap } from "rxjs";
import { City } from "../shared/models/city";
import { Country } from "../shared/models/country";
import { Message } from "../shared/models/message";
import { LocationService } from "../shared/services/location.service";
import { AddMessage, DeleteMessage, FetchCityList, FetchCityListCompleted, FetchCityListFailed, FetchCountryList, FetchCountryListCompleted, FetchCountryListFailed } from "./app-store.actions";
import { AppStateModel, defaultAppState } from "./app-store.model";

@State<AppStateModel>({
    name: 'appState',
    defaults: defaultAppState
})
@Injectable()
export class AppState {
    constructor(private countryService: LocationService) { }

    @Selector()
    public static countryList(state: AppStateModel): Country[] {
        return state.countryList;
    }
    @Selector()
    public static messageList(state: AppStateModel): Message[] {
        return state.messageList;
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

    @Action(FetchCityList)
    public fetchCityList(ctx : StateContext<AppStateModel>, action: FetchCityList) {
        return this.countryService.getCities().pipe(
            tap((response: City[]) => {
                ctx.dispatch(new FetchCityListCompleted(response));
            }),
            catchError((error: HttpErrorResponse) => {
                return ctx.dispatch(new FetchCityListFailed(error));
            })
        );
    }

    @Action(FetchCityListCompleted)
    public fetchCityListCompleted(ctx: StateContext<AppStateModel>, action: FetchCityListCompleted) {
        ctx.setState({
            ...ctx.getState(),
            cityList: action.cities
        });
    }

    @Action(FetchCityListFailed)
    public fetchCityListFailed(ctx: StateContext<AppStateModel>, action: FetchCityListFailed) {
        console.log(action.error);
    }

    @Action(AddMessage)
    public addMessage(ctx: StateContext<AppStateModel>, action: AddMessage) {
        ctx.setState({
            ...ctx.getState(),
            messageList: [...ctx.getState().messageList, action.message]
        });
    }

    @Action(DeleteMessage)
    public deleteMessage(ctx: StateContext<AppStateModel>, action: DeleteMessage) {
        ctx.setState({
            ...ctx.getState(),
            messageList: ctx.getState().messageList.filter((el) => el != action.message)
        });
    }
}