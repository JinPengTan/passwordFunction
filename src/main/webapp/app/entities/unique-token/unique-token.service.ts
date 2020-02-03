import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUniqueToken } from 'app/shared/model/unique-token.model';

type EntityResponseType = HttpResponse<IUniqueToken>;
type EntityArrayResponseType = HttpResponse<IUniqueToken[]>;

@Injectable({ providedIn: 'root' })
export class UniqueTokenService {
  public resourceUrl = SERVER_API_URL + 'api/unique-tokens';

  constructor(protected http: HttpClient) {}

  create(uniqueToken: IUniqueToken): Observable<EntityResponseType> {
    return this.http.post<IUniqueToken>(this.resourceUrl, uniqueToken, { observe: 'response' });
  }

  update(uniqueToken: IUniqueToken): Observable<EntityResponseType> {
    return this.http.put<IUniqueToken>(this.resourceUrl, uniqueToken, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUniqueToken>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUniqueToken[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
