import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import {map} from "rxjs/operators";

@Injectable({ providedIn: 'root' })
export class PasswordService {
  constructor(private http: HttpClient) {}

  save(newPassword: string, currentPassword: string): Observable<void> {
    return this.http.post(SERVER_API_URL + 'api/account/change-password', { currentPassword, newPassword }, {observe: "response"})
      .pipe(map(response => {
        console.log("MODIFIEDDDDDDDDDD CHECKER");
        sessionStorage.setItem('PasswordChecker', <string>response.headers.get('PasswordChecker'));
      }));
  }
}
