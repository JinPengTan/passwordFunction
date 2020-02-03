import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { ApiKeyComponent } from './api-key.component';
import { ApiKeyDetailComponent } from './api-key-detail.component';
import { ApiKeyUpdateComponent } from './api-key-update.component';
import { ApiKeyDeleteDialogComponent } from './api-key-delete-dialog.component';
import { apiKeyRoute } from './api-key.route';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(apiKeyRoute)],
  declarations: [ApiKeyComponent, ApiKeyDetailComponent, ApiKeyUpdateComponent, ApiKeyDeleteDialogComponent],
  entryComponents: [ApiKeyDeleteDialogComponent]
})
export class JhipsterApiKeyModule {}
