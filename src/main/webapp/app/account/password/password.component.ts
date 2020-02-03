import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { PasswordService } from './password.service';

@Component({
  selector: 'jhi-password',
  templateUrl: './password.component.html'
})
export class PasswordComponent implements OnInit {
  doNotMatch = false;
  error = false;
  reuse = false;
  success = false;
  account$?: Observable<Account | null>;
  passwordForm = this.fb.group({
    currentPassword: ['', [Validators.required]],
    newPassword: [
      '',
      [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern('^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[@#%$!&*])[a-zA-Z0-9@#%$!&*]{8,50}$')
      ]
    ],
    confirmPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(50)]]
  });

  constructor(private passwordService: PasswordService, private accountService: AccountService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.account$ = this.accountService.identity();
  }

  changePassword(): void {
    this.error = false;
    this.success = false;
    this.doNotMatch = false;
    this.reuse = false;

    const newPassword = this.passwordForm.get(['newPassword'])!.value;
    if (newPassword !== this.passwordForm.get(['confirmPassword'])!.value) {
      this.doNotMatch = true;
    } else {
      this.passwordService.save(newPassword, this.passwordForm.get(['currentPassword'])!.value).subscribe(
        () => {
          this.success = true;
        },
        error => {
          console.log(error);
          console.log(error.status);
          console.log(error.status === 417);
          if (error.status === 417) {
            console.log('resue');
            this.reuse = true;
          } else {
            console.log('error');
            this.error = true;
          }
        }
      );
    }
  }
}
