import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { ICycle, Cycle } from 'app/shared/model/cycle.model';
import { CycleService } from './cycle.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-cycle-update',
  templateUrl: './cycle-update.component.html'
})
export class CycleUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];
  cycleDatetimeDp: any;

  editForm = this.fb.group({
    id: [],
    cycleCount: [],
    cycleDatetime: [],
    cyclePassword: [],
    user: []
  });

  constructor(
    protected cycleService: CycleService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cycle }) => {
      this.updateForm(cycle);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(cycle: ICycle): void {
    this.editForm.patchValue({
      id: cycle.id,
      cycleCount: cycle.cycleCount,
      cycleDatetime: cycle.cycleDatetime,
      cyclePassword: cycle.cyclePassword,
      user: cycle.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cycle = this.createFromForm();
    if (cycle.id !== undefined) {
      this.subscribeToSaveResponse(this.cycleService.update(cycle));
    } else {
      this.subscribeToSaveResponse(this.cycleService.create(cycle));
    }
  }

  private createFromForm(): ICycle {
    return {
      ...new Cycle(),
      id: this.editForm.get(['id'])!.value,
      cycleCount: this.editForm.get(['cycleCount'])!.value,
      cycleDatetime: this.editForm.get(['cycleDatetime'])!.value,
      cyclePassword: this.editForm.get(['cyclePassword'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICycle>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
