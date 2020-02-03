import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExtendUser } from 'app/shared/model/extend-user.model';

@Component({
  selector: 'jhi-extend-user-detail',
  templateUrl: './extend-user-detail.component.html'
})
export class ExtendUserDetailComponent implements OnInit {
  extendUser: IExtendUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extendUser }) => {
      this.extendUser = extendUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
