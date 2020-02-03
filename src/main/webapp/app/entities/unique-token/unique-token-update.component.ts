import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUniqueToken, UniqueToken } from 'app/shared/model/unique-token.model';
import { UniqueTokenService } from './unique-token.service';

@Component({
  selector: 'jhi-unique-token-update',
  templateUrl: './unique-token-update.component.html'
})
export class UniqueTokenUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    walletId: [],
    tpan: []
  });

  constructor(protected uniqueTokenService: UniqueTokenService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ uniqueToken }) => {
      this.updateForm(uniqueToken);
    });
  }

  updateForm(uniqueToken: IUniqueToken): void {
    this.editForm.patchValue({
      id: uniqueToken.id,
      walletId: uniqueToken.walletId,
      tpan: uniqueToken.tpan
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const uniqueToken = this.createFromForm();
    if (uniqueToken.id !== undefined) {
      this.subscribeToSaveResponse(this.uniqueTokenService.update(uniqueToken));
    } else {
      this.subscribeToSaveResponse(this.uniqueTokenService.create(uniqueToken));
    }
  }

  private createFromForm(): IUniqueToken {
    return {
      ...new UniqueToken(),
      id: this.editForm.get(['id'])!.value,
      walletId: this.editForm.get(['walletId'])!.value,
      tpan: this.editForm.get(['tpan'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUniqueToken>>): void {
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
}
