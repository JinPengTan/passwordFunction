import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITokenHistory } from 'app/shared/model/token-history.model';

type EntityResponseType = HttpResponse<ITokenHistory>;
type EntityArrayResponseType = HttpResponse<ITokenHistory[]>;

@Injectable({ providedIn: 'root' })
export class TokenHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/token-histories';

  constructor(protected http: HttpClient) {}

  create(tokenHistory: ITokenHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tokenHistory);
    return this.http
      .post<ITokenHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tokenHistory: ITokenHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tokenHistory);
    return this.http
      .put<ITokenHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITokenHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITokenHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tokenHistory: ITokenHistory): ITokenHistory {
    const copy: ITokenHistory = Object.assign({}, tokenHistory, {
      historyDt: tokenHistory.historyDt && tokenHistory.historyDt.isValid() ? tokenHistory.historyDt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.historyDt = res.body.historyDt ? moment(res.body.historyDt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tokenHistory: ITokenHistory) => {
        tokenHistory.historyDt = tokenHistory.historyDt ? moment(tokenHistory.historyDt) : undefined;
      });
    }
    return res;
  }
}
