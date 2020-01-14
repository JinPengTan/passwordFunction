import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICycle } from 'app/shared/model/cycle.model';
import { CycleService } from './cycle.service';

@Component({
  templateUrl: './cycle-delete-dialog.component.html'
})
export class CycleDeleteDialogComponent {
  cycle?: ICycle;

  constructor(protected cycleService: CycleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cycleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cycleListModification');
      this.activeModal.close();
    });
  }
}
