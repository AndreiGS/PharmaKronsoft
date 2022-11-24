import { User } from "../shared/models/user";


export interface UserStateModel {
    loggedUser?: User
}

export const defaultUserState: UserStateModel = {
    loggedUser: undefined
};