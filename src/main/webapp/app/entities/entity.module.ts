import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cycle',
        loadChildren: () => import('./cycle/cycle.module').then(m => m.JhipsterCycleModule)
      },
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.JhipsterProfileModule)
      },
      {
        path: 'permission',
        loadChildren: () => import('./permission/permission.module').then(m => m.JhipsterPermissionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JhipsterEntityModule {}
