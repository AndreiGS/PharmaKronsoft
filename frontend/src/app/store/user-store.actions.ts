import { User } from "../shared/models/user";


export class SetLoggedUser {
    static readonly type = '[UserState] User saved.';
    constructor(public user: User ) { }
}