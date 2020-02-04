import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { ApiKeyComponent } from './api-key.component';
import { ApiKeyDetailComponent } from './api-key-detail.component';
import { ApiKeyUpdateComponent } from './api-key-update.component';
import { ApiKeyDeleteDialogComponent } from './api-key-delete-dialog.component';
import { apiKeyRoute } from './api-key.route';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  imports: [JhipsterSharedModule, RouterModule.forChild(apiKeyRoute), MatSelectModule],
  declarations: [ApiKeyComponent, ApiKeyDetailComponent, ApiKeyUpdateComponent, ApiKeyDeleteDialogComponent],
  entryComponents: [ApiKeyDeleteDialogComponent]
})
export class JhipsterApiKeyModule {}
