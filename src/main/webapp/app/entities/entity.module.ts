import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      // {
      //   path: 'cycle',
      //   loadChildren: () => import('./cycle/cycle.module').then(m => m.JhipsterCycleModule)
      // },
      // {
      //   path: 'permission',
      //   loadChildren: () => import('./permission/permission.module').then(m => m.JhipsterPermissionModule)
      // },
      {
        path: 'extend-user',
        loadChildren: () => import('./extend-user/extend-user.module').then(m => m.JhipsterExtendUserModule)
      },
      {
        path: 'profile',
        loadChildren: () => import('../admin/profile/profile.module').then(m => m.JhipsterProfileModule)
      },
      {
        path: 'api-key',
        loadChildren: () => import('./api-key/api-key.module').then(m => m.JhipsterApiKeyModule)
      },
      {
        path: 'token',
        loadChildren: () => import('./token/token.module').then(m => m.JhipsterTokenModule)
      },
      {
        path: 'token-history',
        loadChildren: () => import('./token-history/token-history.module').then(m => m.JhipsterTokenHistoryModule)
      },
      {
        path: 'unique-token',
        loadChildren: () => import('./unique-token/unique-token.module').then(m => m.JhipsterUniqueTokenModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterEntityModule {}
