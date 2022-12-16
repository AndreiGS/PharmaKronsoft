import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Constants } from 'src/app/config/constants';
import { AuthService } from '../services/auth.service';

@Injectable()
export class ResponseInterceptor implements HttpInterceptor {
  constructor(public authService: AuthService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      map((event) => {
        if (event instanceof HttpResponse) {
          const jwtToken = event.headers.get(Constants.JWT_HEADER);
          const refreshToken = event.headers.get(Constants.REFRESH_HEADER);
          if (jwtToken && refreshToken) {
            this.authService.setTokens(jwtToken, refreshToken);
          }
        }
        return event;
      })
    );
  }
}
