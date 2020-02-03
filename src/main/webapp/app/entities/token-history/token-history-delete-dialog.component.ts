import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITokenHistory } from 'app/shared/model/token-history.model';
import { TokenHistoryService } from './token-history.service';

@Component({
  templateUrl: './token-history-delete-dialog.component.html'
})
export class TokenHistoryDeleteDialogComponent {
  tokenHistory?: ITokenHistory;

  constructor(
    protected tokenHistoryService: TokenHistoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tokenHistoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tokenHistoryListModification');
      this.activeModal.close();
    });
  }
}
