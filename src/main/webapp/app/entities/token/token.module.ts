import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { TokenComponent } from './token.component';
import { TokenDetailComponent } from './token-detail.component';
import { TokenUpdateComponent } from './token-update.component';
import { TokenDeleteDialogComponent } from './token-delete-dialog.component';
import { tokenRoute } from './token.route';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(tokenRoute), MatSelectModule],
  declarations: [TokenComponent, TokenDetailComponent, TokenUpdateComponent, TokenDeleteDialogComponent],
  entryComponents: [TokenDeleteDialogComponent]
})
export class JhipsterTokenModule {}
