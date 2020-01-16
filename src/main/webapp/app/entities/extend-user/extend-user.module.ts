import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { ExtendUserComponent } from './extend-user.component';
import { ExtendUserDetailComponent } from './extend-user-detail.component';
import { ExtendUserUpdateComponent } from './extend-user-update.component';
import { ExtendUserDeleteDialogComponent } from './extend-user-delete-dialog.component';
import { extendUserRoute } from './extend-user.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(extendUserRoute)],
  declarations: [ExtendUserComponent, ExtendUserDetailComponent, ExtendUserUpdateComponent, ExtendUserDeleteDialogComponent],
  entryComponents: [ExtendUserDeleteDialogComponent]
})
export class JhipsterExtendUserModule {}
