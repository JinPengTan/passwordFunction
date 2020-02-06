import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { BnNgIdleService } from 'bn-ng-idle';

import { AccountService } from 'app/core/auth/account.service';
import { environment } from '../../../environments/environment.prod';

import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { ExpiredComponent } from 'app/shared/expired/expired/expired.component';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';
import { LANGUAGES } from 'app/core/language/language.constants';
import { LoginService } from 'app/core/login/login.service';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html'
})
export class MainComponent implements OnInit {
  languages = LANGUAGES;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private translateService: TranslateService,
    private languageService: JhiLanguageService,
    private sessionStorage1: SessionStorageService,
    private titleService: Title,
    private router: Router,
    private bnIdle: BnNgIdleService,
    private dialog: MatDialog,
    private loginModalService: LoginModalService
  ) {
    this.bnIdle.startWatching(environment.idleSecond).subscribe(res => {
      if (res) {
        if (sessionStorage.getItem('expired') != 'true') {
          console.log('Session Expired');
          sessionStorage.clear();
          localStorage.clear();
          sessionStorage.setItem('expired', 'true');
          this.router.navigate(['']);
          window.location.reload();
        }
      }
    });
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    this.dialog.open(ExpiredComponent, dialogConfig);
  }

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });

    this.translateService.onLangChange.subscribe(() => this.updateTitle());
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorage1.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  passwordChecker(): String | null {
    return sessionStorage.getItem('PasswordChecker');
  }

  logout(): void {
    this.loginService.logout();
    this.router.navigate(['']);
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }

  login(): void {
    this.loginModalService.open();
  }
}
