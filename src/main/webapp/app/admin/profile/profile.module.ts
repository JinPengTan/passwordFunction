import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from 'app/shared/shared.module';
import { ProfileComponent } from './profile.component';
import { ProfileDetailComponent } from './profile-detail.component';
import { ProfileUpdateComponent } from './profile-update.component';
import { ProfileDeleteDialogComponent } from './profile-delete-dialog.component';
import { profileRoute } from './profile.route';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCheckboxModule } from '@angular/material/checkbox';

@NgModule({
  imports: [
    JhipsterSharedModule,
    RouterModule.forChild(profileRoute),
    MatOptionModule,
    MatSelectModule,
    MatRadioModule,
    MatAutocompleteModule,
    MatCheckboxModule
  ],
  declarations: [ProfileComponent, ProfileDetailComponent, ProfileUpdateComponent, ProfileDeleteDialogComponent],
  entryComponents: [ProfileDeleteDialogComponent]
})
export class JhipsterProfileModule {}
