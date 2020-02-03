import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUniqueToken } from 'app/shared/model/unique-token.model';

@Component({
  selector: 'jhi-unique-token-detail',
  templateUrl: './unique-token-detail.component.html'
})
export class UniqueTokenDetailComponent implements OnInit {
  uniqueToken: IUniqueToken | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uniqueToken }) => {
      this.uniqueToken = uniqueToken;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
