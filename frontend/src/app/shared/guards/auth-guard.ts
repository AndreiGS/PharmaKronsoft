import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { AuthService } from "../services/auth.service";
import { UserService } from "../services/user.service";

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    constructor(private authService: AuthService
                , private router: Router) { }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
      ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        const isLogged = this.authService.isLogged();
        if(!isLogged)
            this.router.navigateByUrl('/login');
        return isLogged;
      }
    
}

@Injectable({
    providedIn: 'root'
  })
  export class NegateAuthGuard implements CanActivate {
    constructor(private authService: AuthService
        , private router: Router) { }
  
    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        const isLogged = this.authService.isLogged();
        if(isLogged)
            this.router.navigateByUrl('/');
        return !isLogged;
    }
}