import { Route } from '@angular/router';

import { PasswordResetFinishComponent } from './password-reset-finish.component';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';

export const passwordResetFinishRoute: Route = {
  path: 'reset/finish',
  component: PasswordResetFinishComponent,
  data: {
    authorities: [],
    pageTitle: 'global.menu.account.password'
  },
  canActivate: [UserRouteAccessService]
};
