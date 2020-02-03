import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUniqueToken, UniqueToken } from 'app/shared/model/unique-token.model';
import { UniqueTokenService } from './unique-token.service';
import { UniqueTokenComponent } from './unique-token.component';
import { UniqueTokenDetailComponent } from './unique-token-detail.component';
import { UniqueTokenUpdateComponent } from './unique-token-update.component';

@Injectable({ providedIn: 'root' })
export class UniqueTokenResolve implements Resolve<IUniqueToken> {
  constructor(private service: UniqueTokenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUniqueToken> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((uniqueToken: HttpResponse<UniqueToken>) => {
          if (uniqueToken.body) {
            return of(uniqueToken.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UniqueToken());
  }
}

export const uniqueTokenRoute: Routes = [
  {
    path: '',
    component: UniqueTokenComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterApp.uniqueToken.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UniqueTokenDetailComponent,
    resolve: {
      uniqueToken: UniqueTokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.uniqueToken.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UniqueTokenUpdateComponent,
    resolve: {
      uniqueToken: UniqueTokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.uniqueToken.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UniqueTokenUpdateComponent,
    resolve: {
      uniqueToken: UniqueTokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.uniqueToken.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
