import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICycle, Cycle } from 'app/shared/model/cycle.model';
import { CycleService } from './cycle.service';
import { CycleComponent } from './cycle.component';
import { CycleDetailComponent } from './cycle-detail.component';
import { CycleUpdateComponent } from './cycle-update.component';

@Injectable({ providedIn: 'root' })
export class CycleResolve implements Resolve<ICycle> {
  constructor(private service: CycleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICycle> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cycle: HttpResponse<Cycle>) => {
          if (cycle.body) {
            return of(cycle.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cycle());
  }
}

export const cycleRoute: Routes = [
  {
    path: '',
    component: CycleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CYCLE_READ'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CycleDetailComponent,
    resolve: {
      cycle: CycleResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CYCLE_READ'],
      pageTitle: 'jhipsterApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CycleUpdateComponent,
    resolve: {
      cycle: CycleResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CYCLE_ADD'],
      pageTitle: 'jhipsterApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CycleUpdateComponent,
    resolve: {
      cycle: CycleResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_CYCLE_UPDATE'],
      pageTitle: 'jhipsterApp.cycle.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
