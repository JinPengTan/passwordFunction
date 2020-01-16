import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IExtendUser, ExtendUser } from 'app/shared/model/extend-user.model';
import { ExtendUserService } from './extend-user.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile/profile.service';

type SelectableEntity = IUser | IProfile;

@Component({
  selector: 'jhi-extend-user-update',
  templateUrl: './extend-user-update.component.html'
})
export class ExtendUserUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  profiles: IProfile[] = [];

  editForm = this.fb.group({
    id: [],
    user: [],
    profiles: []
  });

  constructor(
    protected extendUserService: ExtendUserService,
    protected userService: UserService,
    protected profileService: ProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ extendUser }) => {
      this.updateForm(extendUser);

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));

      this.profileService
        .query()
        .pipe(
          map((res: HttpResponse<IProfile[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProfile[]) => (this.profiles = resBody));
    });
  }

  updateForm(extendUser: IExtendUser): void {
    this.editForm.patchValue({
      id: extendUser.id,
      user: extendUser.user,
      profiles: extendUser.profiles
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const extendUser = this.createFromForm();
    if (extendUser.id !== undefined) {
      this.subscribeToSaveResponse(this.extendUserService.update(extendUser));
    } else {
      this.subscribeToSaveResponse(this.extendUserService.create(extendUser));
    }
  }

  private createFromForm(): IExtendUser {
    return {
      ...new ExtendUser(),
      id: this.editForm.get(['id'])!.value,
      user: this.editForm.get(['user'])!.value,
      profiles: this.editForm.get(['profiles'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExtendUser>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: IProfile[], option: IProfile): IProfile {
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
