import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JhipsterSharedModule } from 'app/shared/shared.module';
import { JhipsterCoreModule } from 'app/core/core.module';
import { JhipsterAppRoutingModule } from './app-routing.module';
import { JhipsterHomeModule } from './home/home.module';
import { JhipsterEntityModule } from './entities/entity.module';

import { MatDialogModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { ExpiredComponent } from 'app/shared/expired/expired/expired.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    JhipsterSharedModule,
    JhipsterCoreModule,
    JhipsterHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JhipsterEntityModule,
    JhipsterAppRoutingModule,
    MatDialogModule,
    ReactiveFormsModule,
    FormsModule,
    MatSidenavModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatButtonModule
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    ExpiredComponent
  ],
  bootstrap: [MainComponent],
  entryComponents: [ExpiredComponent]
})
export class JhipsterAppModule {}
