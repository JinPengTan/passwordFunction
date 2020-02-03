import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IApiKey } from 'app/shared/model/api-key.model';

type EntityResponseType = HttpResponse<IApiKey>;
type EntityArrayResponseType = HttpResponse<IApiKey[]>;

@Injectable({ providedIn: 'root' })
export class ApiKeyService {
  public resourceUrl = SERVER_API_URL + 'api/api-keys';

  constructor(protected http: HttpClient) {}

  create(apiKey: IApiKey): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(apiKey);
    return this.http
      .post<IApiKey>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(apiKey: IApiKey): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(apiKey);
    return this.http
      .put<IApiKey>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApiKey>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApiKey[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(apiKey: IApiKey): IApiKey {
    const copy: IApiKey = Object.assign({}, apiKey, {
      modifiedDate: apiKey.modifiedDate && apiKey.modifiedDate.isValid() ? apiKey.modifiedDate.toJSON() : undefined,
      createdDate: apiKey.createdDate && apiKey.createdDate.isValid() ? apiKey.createdDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.modifiedDate = res.body.modifiedDate ? moment(res.body.modifiedDate) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((apiKey: IApiKey) => {
        apiKey.modifiedDate = apiKey.modifiedDate ? moment(apiKey.modifiedDate) : undefined;
        apiKey.createdDate = apiKey.createdDate ? moment(apiKey.createdDate) : undefined;
      });
    }
    return res;
  }
}
