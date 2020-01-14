import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CycleService } from 'app/entities/cycle/cycle.service';
import { ICycle, Cycle } from 'app/shared/model/cycle.model';

describe('Service Tests', () => {
  describe('Cycle Service', () => {
    let injector: TestBed;
    let service: CycleService;
    let httpMock: HttpTestingController;
    let elemDefault: ICycle;
    let expectedResult: ICycle | ICycle[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CycleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Cycle(0, 0, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            cycleDatetime: currentDate.format(DATE_FORMAT)
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

      it('should create a Cycle', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            cycleDatetime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            cycleDatetime: currentDate
          },
          returnedFromService
        );
        service
          .create(new Cycle())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Cycle', () => {
        const returnedFromService = Object.assign(
          {
            cycleCount: 1,
            cycleDatetime: currentDate.format(DATE_FORMAT),
            cyclePassword: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            cycleDatetime: currentDate
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

      it('should return a list of Cycle', () => {
        const returnedFromService = Object.assign(
          {
            cycleCount: 1,
            cycleDatetime: currentDate.format(DATE_FORMAT),
            cyclePassword: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            cycleDatetime: currentDate
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

      it('should delete a Cycle', () => {
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
