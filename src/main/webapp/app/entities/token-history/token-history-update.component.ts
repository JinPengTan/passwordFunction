import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITokenHistory, TokenHistory } from 'app/shared/model/token-history.model';
import { TokenHistoryService } from './token-history.service';

@Component({
  selector: 'jhi-token-history-update',
  templateUrl: './token-history-update.component.html'
})
export class TokenHistoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tpan: [],
    hashKey: [],
    tokenExpiry: [],
    tokenStatus: [],
    maskPan: [],
    enPan: [],
    expiryDate: [],
    trId: [],
    version: [],
    historyDt: []
  });

  constructor(protected tokenHistoryService: TokenHistoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tokenHistory }) => {
      this.updateForm(tokenHistory);
    });
  }

  updateForm(tokenHistory: ITokenHistory): void {
    this.editForm.patchValue({
      id: tokenHistory.id,
      tpan: tokenHistory.tpan,
      hashKey: tokenHistory.hashKey,
      tokenExpiry: tokenHistory.tokenExpiry,
      tokenStatus: tokenHistory.tokenStatus,
      maskPan: tokenHistory.maskPan,
      enPan: tokenHistory.enPan,
      expiryDate: tokenHistory.expiryDate,
      trId: tokenHistory.trId,
      version: tokenHistory.version,
      historyDt: tokenHistory.historyDt != null ? tokenHistory.historyDt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tokenHistory = this.createFromForm();
    if (tokenHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.tokenHistoryService.update(tokenHistory));
    } else {
      this.subscribeToSaveResponse(this.tokenHistoryService.create(tokenHistory));
    }
  }

  private createFromForm(): ITokenHistory {
    return {
      ...new TokenHistory(),
      id: this.editForm.get(['id'])!.value,
      tpan: this.editForm.get(['tpan'])!.value,
      hashKey: this.editForm.get(['hashKey'])!.value,
      tokenExpiry: this.editForm.get(['tokenExpiry'])!.value,
      tokenStatus: this.editForm.get(['tokenStatus'])!.value,
      maskPan: this.editForm.get(['maskPan'])!.value,
      enPan: this.editForm.get(['enPan'])!.value,
      expiryDate: this.editForm.get(['expiryDate'])!.value,
      trId: this.editForm.get(['trId'])!.value,
      version: this.editForm.get(['version'])!.value,
      historyDt:
        this.editForm.get(['historyDt'])!.value != null ? moment(this.editForm.get(['historyDt'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITokenHistory>>): void {
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
