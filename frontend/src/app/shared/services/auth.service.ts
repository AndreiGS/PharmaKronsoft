import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { Constants } from 'src/app/config/constants';
import { User } from '../models/user';
import { UserRegisterDto } from '../models/user-dtos';
import { UserLoginDTO as UserLoginDto } from '../models/user-dtos';

@UntilDestroy()
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private jwtAccessToken: string | null = '';
  private refreshToken: string | null = '';

  constructor(private httpClient: HttpClient, private router: Router) {
    this.jwtAccessToken = localStorage.getItem('accessToken');
    this.refreshToken = localStorage.getItem('refreshToken');
  }

  public get getJwtAccessToken() {
    return this.jwtAccessToken;
  }

  public get getRefreshToken() {
    return this.refreshToken;
  }

  public login(userLoginDTO: UserLoginDto) {
    this.httpClient
      .post<void>(Constants.AUTH_LOGIN_API, userLoginDTO, {
        observe: 'response',
      })
      .pipe(untilDestroyed(this))
      .subscribe({
        next: (response: HttpResponse<void>) => {
          this.setTokens(
            response.headers.get(Constants.JWT_HEADER),
            response.headers.get(Constants.REFRESH_HEADER)
          );
          this.router.navigateByUrl('/');
        },
      });
  }

  public register(user: UserRegisterDto) {
    this.httpClient
      .post<void>(Constants.AUTH_REGISTER_API, user, {
        observe: 'response',
      })
      .pipe(untilDestroyed(this))
      .subscribe({
        next: (response: HttpResponse<void>) => {
          this.setTokens(
            response.headers.get(Constants.JWT_HEADER),
            response.headers.get(Constants.REFRESH_HEADER)
          );
          this.router.navigateByUrl('/');
        },
      });
  }

  public logout() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    this.jwtAccessToken = this.refreshToken = null;
  }

  public isLogged(): boolean {
    return this.jwtAccessToken != null && this.refreshToken != null;
  }

  public setTokens(jwtToken: string | null, refreshToken: string | null) {
    if (jwtToken) {
      localStorage.setItem('accessToken', jwtToken);
    } else {
      localStorage.removeItem('accessToken');
    }

    if (refreshToken) {
      localStorage.setItem('refreshToken', refreshToken);
    } else {
      localStorage.removeItem('refreshToken');
    }

    this.jwtAccessToken = jwtToken;
    this.refreshToken = refreshToken;
  }
}
