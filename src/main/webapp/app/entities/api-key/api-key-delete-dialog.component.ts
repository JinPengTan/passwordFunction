import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApiKey } from 'app/shared/model/api-key.model';
import { ApiKeyService } from './api-key.service';

@Component({
  templateUrl: './api-key-delete-dialog.component.html'
})
export class ApiKeyDeleteDialogComponent {
  apiKey?: IApiKey;

  constructor(protected apiKeyService: ApiKeyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.apiKeyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('apiKeyListModification');
      this.activeModal.close();
    });
  }
}
