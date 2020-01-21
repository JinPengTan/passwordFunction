import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { Form, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { IPermission } from 'app/shared/model/permission.model';
import { PermissionService } from 'app/entities/permission/permission.service';

export interface PermissionGroup {
  name: string;
  permit: IPermission[];
}

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
  isSaving = false;

  permissions: IPermission[] = [];
  permitList: IPermission[] = [];
  optionList: PermissionGroup[] = [];

  editForm = this.fb.group({
    id: [],
    role: [],
    permissions: this.fb.array([])
  });

  constructor(
    protected profileService: ProfileService,
    protected permissionService: PermissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);

      this.permissionService
        .query()
        .pipe(
          map((res: HttpResponse<IPermission[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPermission[]) => (this.permissions = resBody));
    });

    this.profileService.getPermission().subscribe(data => {
      this.permitList = data;
      this.optionList.push({ name: 'CYCLE', permit: [] });
      this.optionList.push({ name: 'PERMISSION', permit: [] });
      this.optionList.push({ name: 'EXTENDUSER', permit: [] });
      this.optionList.push({ name: 'PROFILE', permit: [] });
      this.optionList.push({ name: 'TOKEN', permit: [] });
      this.optionList.push({ name: 'HISTORY', permit: [] });
      this.optionList.push({ name: 'ROLE', permit: [] });

      this.permitList.forEach(permit => {
        if (permit.name.includes('CYCLE')) this.optionList[0].permit.push(permit);
        else if (permit.name.includes('PERMISSION')) this.optionList[1].permit.push(permit);
        else if (permit.name.includes('EXTENDUSER')) this.optionList[2].permit.push(permit);
        else if (permit.name.includes('PROFILE')) this.optionList[3].permit.push(permit);
        else if (permit.name.includes('TOKEN')) this.optionList[4].permit.push(permit);
        else if (permit.name.includes('HISTORY')) this.optionList[5].permit.push(permit);
        else this.optionList[6].permit.push(permit);
      });
    });

    console.log(this.optionList);
  }

  onCheckboxChange(e) {
    const checkArray: FormArray = this.editForm.get('permissions') as FormArray;

    if (e.target.checked) {
      for (let i = 0; i < this.permitList.length; i++) {
        console.log(this.permitList[i].name + ' /////// ' + e.target.value);
        if (this.permitList[i].name === e.target.value) {
          checkArray.push(new FormControl(this.permitList[i]));
          console.log(this.permitList[i]);
          return;
        }
      }
    } else {
      let i: number = 0;
      checkArray.controls.forEach(item => {
        console.log(this.permitList[i]);
        if (item.value == e.target.name) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }
  }

  updateForm(profile: IProfile): void {
    this.editForm.patchValue({
      id: profile.id,
      role: profile.role,
      permissions: profile.permissions
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id'])!.value,
      role: this.editForm.get(['role'])!.value,
      permissions: this.editForm.get(['permissions'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
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

  trackById(index: number, item: IPermission): any {
    return item.id;
  }

  getSelected(selectedVals: IPermission[], option: IPermission): IPermission {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
