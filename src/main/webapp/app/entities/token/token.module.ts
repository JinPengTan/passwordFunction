import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { TokenComponent } from './token.component';
import { TokenDetailComponent } from './token-detail.component';
import { TokenUpdateComponent } from './token-update.component';
import { TokenDeleteDialogComponent } from './token-delete-dialog.component';
import { tokenRoute } from './token.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(tokenRoute)],
  declarations: [TokenComponent, TokenDetailComponent, TokenUpdateComponent, TokenDeleteDialogComponent],
  entryComponents: [TokenDeleteDialogComponent]
})
export class JhipsterTokenModule {}
