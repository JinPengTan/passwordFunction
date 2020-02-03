import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TokenHistoryService } from 'app/entities/token-history/token-history.service';
import { ITokenHistory, TokenHistory } from 'app/shared/model/token-history.model';

describe('Service Tests', () => {
  describe('TokenHistory Service', () => {
    let injector: TestBed;
    let service: TokenHistoryService;
    let httpMock: HttpTestingController;
    let elemDefault: ITokenHistory;
    let expectedResult: ITokenHistory | ITokenHistory[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TokenHistoryService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TokenHistory(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            historyDt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TokenHistory', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            historyDt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            historyDt: currentDate
          },
          returnedFromService
        );
        service
          .create(new TokenHistory())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TokenHistory', () => {
        const returnedFromService = Object.assign(
          {
            tpan: 'BBBBBB',
            hashKey: 'BBBBBB',
            tokenExpiry: 'BBBBBB',
            tokenStatus: 'BBBBBB',
            maskPan: 'BBBBBB',
            enPan: 'BBBBBB',
            expiryDate: 'BBBBBB',
            trId: 'BBBBBB',
            version: 1,
            historyDt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            historyDt: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TokenHistory', () => {
        const returnedFromService = Object.assign(
          {
            tpan: 'BBBBBB',
            hashKey: 'BBBBBB',
            tokenExpiry: 'BBBBBB',
            tokenStatus: 'BBBBBB',
            maskPan: 'BBBBBB',
            enPan: 'BBBBBB',
            expiryDate: 'BBBBBB',
            trId: 'BBBBBB',
            version: 1,
            historyDt: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            historyDt: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TokenHistory', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
