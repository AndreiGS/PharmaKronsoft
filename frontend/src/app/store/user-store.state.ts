import { Injectable } from "@angular/core";
import { Action, State, StateContext } from "@ngxs/store";
import { SetLoggedUser } from "./user-store.actions";
import { defaultUserState, UserStateModel } from "./user-store.model";


@State<UserStateModel>({
    name: 'user',
    defaults: defaultUserState
})
@Injectable()
export class UserState {
    constructor() { }

    @Action(SetLoggedUser)
    public setLoggedUser(ctx: StateContext<UserStateModel>, action: SetLoggedUser) {
        ctx.setState({
            ...ctx.getState(),
            loggedUser: action.user            
        });
    }
}