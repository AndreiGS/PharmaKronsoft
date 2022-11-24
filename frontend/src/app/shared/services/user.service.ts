import { Injectable } from '@angular/core';
import { delay, Observable, of, switchMap, timer } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  waitAfterLastChange: number = 300;

  constructor() { }

  public checkIfUsernameExists(username: string): Observable<boolean> {
    return timer(this.waitAfterLastChange).pipe(switchMap(() => {
      return of(username == 'uexist').pipe(delay(200))
    }));
  }

}
