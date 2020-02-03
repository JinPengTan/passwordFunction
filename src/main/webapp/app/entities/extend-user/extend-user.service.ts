import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ExtendUser, IExtendUser } from 'app/shared/model/extend-user.model';

type EntityResponseType = HttpResponse<IExtendUser>;
type EntityArrayResponseType = HttpResponse<IExtendUser[]>;

@Injectable({ providedIn: 'root' })
export class ExtendUserService {
  public resourceUrl = SERVER_API_URL + 'api/extend-users';

  constructor(protected http: HttpClient) {}

  create(extendUser: IExtendUser): Observable<EntityResponseType> {
    return this.http.post<IExtendUser>(this.resourceUrl, extendUser, { observe: 'response' });
  }

  update(extendUser: IExtendUser): Observable<EntityResponseType> {
    return this.http.put<IExtendUser>(this.resourceUrl, extendUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IExtendUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IExtendUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<ExtendUser[]> {
    return this.http.get<ExtendUser[]>(SERVER_API_URL + 'api/allExtendUsers');
  }
}
