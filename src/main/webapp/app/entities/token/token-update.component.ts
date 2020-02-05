import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IToken, Token } from 'app/shared/model/token.model';
import { TokenService } from './token.service';

@Component({
  selector: 'jhi-token-update',
  templateUrl: './token-update.component.html'
})
export class TokenUpdateComponent implements OnInit {
  isSaving = false;
  selectedStatus: string = '';

  editForm = this.fb.group({
    id: [],
    walletId: [],
    tpan: [],
    hashKey: [],
    tokenExpiry: [],
    tokenStatus: [],
    maskPan: [],
    enPan: [],
    expiryDate: [],
    version: []
  });

  constructor(protected tokenService: TokenService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ token }) => {
      this.updateForm(token);
    });
  }

  updateForm(token: IToken): void {
    this.editForm.patchValue({
      id: token.id,
      walletId: token.walletId,
      tpan: token.tpan,
      hashKey: token.hashKey,
      tokenExpiry: token.tokenExpiry,
      tokenStatus: token.tokenStatus,
      maskPan: token.maskPan,
      enPan: token.enPan,
      expiryDate: token.expiryDate,
      version: token.version
    });

    // @ts-ignore
    this.selectedStatus = this.editForm.get('tokenStatus').value;
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const token = this.createFromForm();
    if (token.id !== undefined) {
      this.subscribeToSaveResponse(this.tokenService.update(token));
    } else {
      this.subscribeToSaveResponse(this.tokenService.create(token));
    }
  }

  private createFromForm(): IToken {
    return {
      ...new Token(),
      id: this.editForm.get(['id'])!.value,
      walletId: this.editForm.get(['walletId'])!.value,
      tpan: this.editForm.get(['tpan'])!.value,
      hashKey: this.editForm.get(['hashKey'])!.value,
      tokenExpiry: this.editForm.get(['tokenExpiry'])!.value,
      tokenStatus: this.editForm.get(['tokenStatus'])!.value,
      maskPan: this.editForm.get(['maskPan'])!.value,
      enPan: this.editForm.get(['enPan'])!.value,
      expiryDate: this.editForm.get(['expiryDate'])!.value,
      version: this.editForm.get(['version'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IToken>>): void {
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
