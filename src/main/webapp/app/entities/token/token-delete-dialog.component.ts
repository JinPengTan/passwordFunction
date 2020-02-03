import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IToken } from 'app/shared/model/token.model';
import { TokenService } from './token.service';

@Component({
  templateUrl: './token-delete-dialog.component.html'
})
export class TokenDeleteDialogComponent {
  token?: IToken;

  constructor(protected tokenService: TokenService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tokenService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tokenListModification');
      this.activeModal.close();
    });
  }
}
