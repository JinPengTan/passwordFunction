import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IApiKey, ApiKey } from 'app/shared/model/api-key.model';
import { ApiKeyService } from './api-key.service';
import { ApiKeyComponent } from './api-key.component';
import { ApiKeyDetailComponent } from './api-key-detail.component';
import { ApiKeyUpdateComponent } from './api-key-update.component';

@Injectable({ providedIn: 'root' })
export class ApiKeyResolve implements Resolve<IApiKey> {
  constructor(private service: ApiKeyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IApiKey> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((apiKey: HttpResponse<ApiKey>) => {
          if (apiKey.body) {
            return of(apiKey.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ApiKey());
  }
}

export const apiKeyRoute: Routes = [
  {
    path: '',
    component: ApiKeyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_APIKEY_READ'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterApp.apiKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApiKeyDetailComponent,
    resolve: {
      apiKey: ApiKeyResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_APIKEY_READ'],
      pageTitle: 'jhipsterApp.apiKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApiKeyUpdateComponent,
    resolve: {
      apiKey: ApiKeyResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_APIKEY_ADD'],
      pageTitle: 'jhipsterApp.apiKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApiKeyUpdateComponent,
    resolve: {
      apiKey: ApiKeyResolve
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_APIKEY_UPDATE'],
      pageTitle: 'jhipsterApp.apiKey.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
