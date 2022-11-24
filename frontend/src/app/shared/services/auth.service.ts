import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../models/user';
import { UserLoginDTO } from '../models/user-dtos';
import { JWTToken } from '../utils/jwt-token.utils';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private jwtAccessToken: string | null = '';
  private refreshToken: string | null = '';

  constructor(public localStorageService: LocalStorageService) {
    this.jwtAccessToken = this.localStorageService.get('accessToken');
    this.refreshToken = this.localStorageService.get('refreshToken');
  }
  
  public login(userLoginDTO: UserLoginDTO) {
    
  }

  public register(user: User) {

  }

  public logout() {
    
  }

  
  public isLogged(): Observable<boolean> | boolean {
    if(this.jwtAccessToken == null || this.refreshToken == null)
      return false;
    if(JWTToken.isExpired(this.jwtAccessToken)) {
      this.refreshTokens();
    }
    return true;
  }

  public refreshTokens() {
    
  }

  

  public get getJwtAccessToken() {
    return this.jwtAccessToken;
  }
}
