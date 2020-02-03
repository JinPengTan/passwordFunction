import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUniqueToken } from 'app/shared/model/unique-token.model';
import { UniqueTokenService } from './unique-token.service';

@Component({
  templateUrl: './unique-token-delete-dialog.component.html'
})
export class UniqueTokenDeleteDialogComponent {
  uniqueToken?: IUniqueToken;

  constructor(
    protected uniqueTokenService: UniqueTokenService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.uniqueTokenService.delete(id).subscribe(() => {
      this.eventManager.broadcast('uniqueTokenListModification');
      this.activeModal.close();
    });
  }
}
