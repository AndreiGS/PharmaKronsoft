import { Injectable } from "@angular/core";
import { Store } from "@ngxs/store";
import { User } from "../shared/models/user";
import { SetLoggedUser } from "./user-store.actions";
import { UserStateModel } from "./user-store.model";
import { UserState } from "./user-store.state";

@Injectable()
export class UserStoreService {
    constructor(private store : Store) { }

    public get currentUserStoreSnapshot(): UserStateModel {
        return this.store.selectSnapshot(UserState);
    }

    public setLoggedUser(user: User) {
        this.store.dispatch(new SetLoggedUser(user));
    } 

}