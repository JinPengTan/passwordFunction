import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { TokenHistoryComponent } from './token-history.component';
import { TokenHistoryDetailComponent } from './token-history-detail.component';
import { TokenHistoryUpdateComponent } from './token-history-update.component';
import { TokenHistoryDeleteDialogComponent } from './token-history-delete-dialog.component';
import { tokenHistoryRoute } from './token-history.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(tokenHistoryRoute)],
  declarations: [TokenHistoryComponent, TokenHistoryDetailComponent, TokenHistoryUpdateComponent, TokenHistoryDeleteDialogComponent],
  entryComponents: [TokenHistoryDeleteDialogComponent]
})
export class JhipsterTokenHistoryModule {}
