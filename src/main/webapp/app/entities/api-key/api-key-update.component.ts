import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IApiKey, ApiKey } from 'app/shared/model/api-key.model';
import { ApiKeyService } from './api-key.service';

@Component({
  selector: 'jhi-api-key-update',
  templateUrl: './api-key-update.component.html'
})
export class ApiKeyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    requestorType: [],
    requestorId: [],
    apiKey: [],
    apiStatus: [],
    modifiedDate: [],
    createdDate: []
  });

  constructor(protected apiKeyService: ApiKeyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ apiKey }) => {
      this.updateForm(apiKey);
    });
  }

  updateForm(apiKey: IApiKey): void {
    this.editForm.patchValue({
      id: apiKey.id,
      requestorType: apiKey.requestorType,
      requestorId: apiKey.requestorId,
      apiKey: apiKey.apiKey,
      apiStatus: apiKey.apiStatus,
      modifiedDate: apiKey.modifiedDate != null ? apiKey.modifiedDate.format(DATE_TIME_FORMAT) : null,
      createdDate: apiKey.createdDate != null ? apiKey.createdDate.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const apiKey = this.createFromForm();
    if (apiKey.id !== undefined) {
      this.subscribeToSaveResponse(this.apiKeyService.update(apiKey));
    } else {
      this.subscribeToSaveResponse(this.apiKeyService.create(apiKey));
    }
  }

  private createFromForm(): IApiKey {
    return {
      ...new ApiKey(),
      id: this.editForm.get(['id'])!.value,
      requestorType: this.editForm.get(['requestorType'])!.value,
      requestorId: this.editForm.get(['requestorId'])!.value,
      apiKey: this.editForm.get(['apiKey'])!.value,
      apiStatus: this.editForm.get(['apiStatus'])!.value,
      modifiedDate:
        this.editForm.get(['modifiedDate'])!.value != null
          ? moment(this.editForm.get(['modifiedDate'])!.value, DATE_TIME_FORMAT)
          : undefined,
      createdDate:
        this.editForm.get(['createdDate'])!.value != null ? moment(this.editForm.get(['createdDate'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApiKey>>): void {
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
