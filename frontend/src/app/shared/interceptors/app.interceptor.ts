import { Constants } from 'src/app/config/constants';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AppInterceptor implements HttpInterceptor {
  constructor(public authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authService.getJwtAccessToken;
    const refreshToken = this.authService.getRefreshToken;
    if (token && refreshToken) {
      req = req.clone({
        url: req.url,
        setHeaders: {
          [Constants.JWT_HEADER]: token,
          [Constants.REFRESH_HEADER]: refreshToken,
        },
      });
    }
    return next.handle(req);
  }
}
