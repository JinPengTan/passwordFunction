import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { UniqueTokenComponent } from './unique-token.component';
import { UniqueTokenDetailComponent } from './unique-token-detail.component';
import { UniqueTokenUpdateComponent } from './unique-token-update.component';
import { UniqueTokenDeleteDialogComponent } from './unique-token-delete-dialog.component';
import { uniqueTokenRoute } from './unique-token.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(uniqueTokenRoute)],
  declarations: [UniqueTokenComponent, UniqueTokenDetailComponent, UniqueTokenUpdateComponent, UniqueTokenDeleteDialogComponent],
  entryComponents: [UniqueTokenDeleteDialogComponent]
})
export class JhipsterUniqueTokenModule {}
