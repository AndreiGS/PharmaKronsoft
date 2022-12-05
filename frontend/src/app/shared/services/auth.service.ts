import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/config/constants';
import { UserStoreService } from 'src/app/store/user-store.service';
import { User } from '../models/user';
import { UserLoginDTO, UserLoginResponseDTO } from '../models/user-dtos';

@UntilDestroy()
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private jwtAccessToken: string | null = '';
  private refreshToken: string | null = '';

  constructor(
    private httpClient: HttpClient,
    private router: Router,
    private userStoreService: UserStoreService
  ) {
    this.jwtAccessToken = localStorage.getItem('accessToken');
    this.refreshToken = localStorage.getItem('refreshToken');
  }

  public login(userLoginDTO: UserLoginDTO) {
    this.httpClient
      .post<UserLoginResponseDTO>(Constants.AUTH_LOGIN_API, userLoginDTO)
      .pipe(untilDestroyed(this))
      .subscribe({
        next: (response: UserLoginResponseDTO) => {
          // TODO: what response ??
          // this.router.navigateByUrl('/');
        },
      });
  }

  public register(user: User) {}

  public logout() {}

  public isLogged(): Observable<boolean> | boolean {
    if (this.jwtAccessToken == null || this.refreshToken == null) return false;
    // if(JWTToken.isExpired(this.jwtAccessToken)) {
    //   this.refreshTokens();
    // }
    return true;
  }

  public refreshTokens() {}

  public get getJwtAccessToken() {
    return this.jwtAccessToken;
  }

  public setTokens(jwtToken: string, refreshToken: string) {
    localStorage.setItem('accessToken', jwtToken);
    localStorage.setItem('refreshToken', refreshToken);
    this.jwtAccessToken = jwtToken;
    this.refreshToken = refreshToken;
  }
}
