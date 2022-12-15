import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Constants } from 'src/app/config/constants';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';

@UntilDestroy()
@Injectable({
  providedIn: 'root',
})
export class UserService {
  waitAfterLastChange: number = 300;

  constructor(private httpClient: HttpClient) {}

  public checkIfUsernameExists(username: string): Observable<boolean> {
    return this.httpClient
      .post<boolean>(
        Constants.USER_USERNAME_EXISTS_API,
        {},
        {
          params: {
            username: username,
          },
        }
      )
      .pipe(untilDestroyed(this))
      .pipe(map((result: boolean) => result));
  }
}
