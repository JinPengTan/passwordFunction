import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITokenHistory } from 'app/shared/model/token-history.model';

@Component({
  selector: 'jhi-token-history-detail',
  templateUrl: './token-history-detail.component.html'
})
export class TokenHistoryDetailComponent implements OnInit {
  tokenHistory: ITokenHistory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tokenHistory }) => {
      this.tokenHistory = tokenHistory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
