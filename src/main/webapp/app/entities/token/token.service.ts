import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IToken } from 'app/shared/model/token.model';

type EntityResponseType = HttpResponse<IToken>;
type EntityArrayResponseType = HttpResponse<IToken[]>;

@Injectable({ providedIn: 'root' })
export class TokenService {
  public resourceUrl = SERVER_API_URL + 'api/tokens';

  constructor(protected http: HttpClient) {}

  create(token: IToken): Observable<EntityResponseType> {
    return this.http.post<IToken>(this.resourceUrl, token, { observe: 'response' });
  }

  update(token: IToken): Observable<EntityResponseType> {
    return this.http.put<IToken>(this.resourceUrl, token, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IToken>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IToken[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
