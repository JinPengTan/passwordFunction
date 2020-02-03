import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IToken, Token } from 'app/shared/model/token.model';
import { TokenService } from './token.service';
import { TokenComponent } from './token.component';
import { TokenDetailComponent } from './token-detail.component';
import { TokenUpdateComponent } from './token-update.component';

@Injectable({ providedIn: 'root' })
export class TokenResolve implements Resolve<IToken> {
  constructor(private service: TokenService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IToken> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((token: HttpResponse<Token>) => {
          if (token.body) {
            return of(token.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Token());
  }
}

export const tokenRoute: Routes = [
  {
    path: '',
    component: TokenComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterApp.token.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TokenDetailComponent,
    resolve: {
      token: TokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.token.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TokenUpdateComponent,
    resolve: {
      token: TokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.token.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TokenUpdateComponent,
    resolve: {
      token: TokenResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipsterApp.token.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
