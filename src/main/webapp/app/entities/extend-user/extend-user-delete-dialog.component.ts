import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExtendUser } from 'app/shared/model/extend-user.model';
import { ExtendUserService } from './extend-user.service';

@Component({
  templateUrl: './extend-user-delete-dialog.component.html'
})
export class ExtendUserDeleteDialogComponent {
  extendUser?: IExtendUser;

  constructor(
    protected extendUserService: ExtendUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.extendUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('extendUserListModification');
      this.activeModal.close();
    });
  }
}
