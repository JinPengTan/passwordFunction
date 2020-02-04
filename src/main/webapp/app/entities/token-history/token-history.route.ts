import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITokenHistory, TokenHistory } from 'app/shared/model/token-history.model';
import { TokenHistoryService } from './token-history.service';
import { TokenHistoryComponent } from './token-history.component';
import { TokenHistoryDetailComponent } from './token-history-detail.component';
import { TokenHistoryUpdateComponent } from './token-history-update.component';

@Injectable({ providedIn: 'root' })
export class TokenHistoryResolve implements Resolve<ITokenHistory> {
  constructor(private service: TokenHistoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITokenHistory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tokenHistory: HttpResponse<TokenHistory>) => {
          if (tokenHistory.body) {
            return of(tokenHistory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TokenHistory());
  }
}

export const tokenHistoryRoute: Routes = [
  {
    path: '',
    component: TokenHistoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_HISTORY_READ'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterApp.tokenHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TokenHistoryDetailComponent,
    resolve: {
      tokenHistory: TokenHistoryResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_HISTORY_READ'],
      pageTitle: 'jhipsterApp.tokenHistory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
  // {
  //   path: 'new',
  //   component: TokenHistoryUpdateComponent,
  //   resolve: {
  //     tokenHistory: TokenHistoryResolve
  //   },
  //   data: {
  //     authorities: ['ROLE_USER'],
  //     pageTitle: 'jhipsterApp.tokenHistory.home.title'
  //   },
  //   canActivate: [UserRouteAccessService]
  // },
  // {
  //   path: ':id/edit',
  //   component: TokenHistoryUpdateComponent,
  //   resolve: {
  //     tokenHistory: TokenHistoryResolve
  //   },
  //   data: {
  //     authorities: ['ROLE_USER'],
  //     pageTitle: 'jhipsterApp.tokenHistory.home.title'
  //   },
  //   canActivate: [UserRouteAccessService]
  // }
];
