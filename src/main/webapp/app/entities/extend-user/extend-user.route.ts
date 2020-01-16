import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IExtendUser, ExtendUser } from 'app/shared/model/extend-user.model';
import { ExtendUserService } from './extend-user.service';
import { ExtendUserComponent } from './extend-user.component';
import { ExtendUserDetailComponent } from './extend-user-detail.component';
import { ExtendUserUpdateComponent } from './extend-user-update.component';

@Injectable({ providedIn: 'root' })
export class ExtendUserResolve implements Resolve<IExtendUser> {
  constructor(private service: ExtendUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExtendUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((extendUser: HttpResponse<ExtendUser>) => {
          if (extendUser.body) {
            return of(extendUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ExtendUser());
  }
}

export const extendUserRoute: Routes = [
  {
    path: '',
    component: ExtendUserComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterApp.extendUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ExtendUserDetailComponent,
    resolve: {
      extendUser: ExtendUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.extendUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ExtendUserUpdateComponent,
    resolve: {
      extendUser: ExtendUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.extendUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ExtendUserUpdateComponent,
    resolve: {
      extendUser: ExtendUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.extendUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
