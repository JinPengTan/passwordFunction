import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { TokenService } from 'app/entities/token/token.service';
import { IToken, Token } from 'app/shared/model/token.model';

describe('Service Tests', () => {
  describe('Token Service', () => {
    let injector: TestBed;
    let service: TokenService;
    let httpMock: HttpTestingController;
    let elemDefault: IToken;
    let expectedResult: IToken | IToken[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TokenService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Token(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Token', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Token())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Token', () => {
        const returnedFromService = Object.assign(
          {
            walletId: 'BBBBBB',
            tpan: 'BBBBBB',
            hashKey: 'BBBBBB',
            tokenExpiry: 'BBBBBB',
            tokenStatus: 'BBBBBB',
            maskPan: 'BBBBBB',
            enPan: 'BBBBBB',
            expiryDate: 'BBBBBB',
            version: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Token', () => {
        const returnedFromService = Object.assign(
          {
            walletId: 'BBBBBB',
            tpan: 'BBBBBB',
            hashKey: 'BBBBBB',
            tokenExpiry: 'BBBBBB',
            tokenStatus: 'BBBBBB',
            maskPan: 'BBBBBB',
            enPan: 'BBBBBB',
            expiryDate: 'BBBBBB',
            version: 1
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a Token', () => {
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
