import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICycle } from 'app/shared/model/cycle.model';

type EntityResponseType = HttpResponse<ICycle>;
type EntityArrayResponseType = HttpResponse<ICycle[]>;

@Injectable({ providedIn: 'root' })
export class CycleService {
  public resourceUrl = SERVER_API_URL + 'api/cycles';

  constructor(protected http: HttpClient) {}

  create(cycle: ICycle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycle);
    return this.http
      .post<ICycle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cycle: ICycle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycle);
    return this.http
      .put<ICycle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICycle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICycle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cycle: ICycle): ICycle {
    const copy: ICycle = Object.assign({}, cycle, {
      cycleDatetime: cycle.cycleDatetime && cycle.cycleDatetime.isValid() ? cycle.cycleDatetime.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.cycleDatetime = res.body.cycleDatetime ? moment(res.body.cycleDatetime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cycle: ICycle) => {
        cycle.cycleDatetime = cycle.cycleDatetime ? moment(cycle.cycleDatetime) : undefined;
      });
    }
    return res;
  }
}
