import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { BnNgIdleService} from "bn-ng-idle";

import { AccountService } from 'app/core/auth/account.service';
import {environment} from "../../../environments/environment.prod";

import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ExpiredComponent} from "app/shared/expired/expired/expired.component";

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html'
})
export class MainComponent implements OnInit {
  constructor(
    private accountService: AccountService,
    private translateService: TranslateService,
    private titleService: Title,
    private router: Router,
    private bnIdle: BnNgIdleService,
    private dialog: MatDialog
  ) {
    this.bnIdle.startWatching(environment.idleSecond).subscribe((res) => {
      if(res){
        if(sessionStorage.getItem('expired') != 'true') {
          console.log("Session Expired");
          sessionStorage.clear();
          localStorage.clear();
          sessionStorage.setItem('expired', 'true');
          this.router.navigate(['']);
          window.location.reload()
        }
      }
    })
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
}

