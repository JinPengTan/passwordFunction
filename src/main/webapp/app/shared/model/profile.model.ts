import { IUser } from 'app/core/user/user.model';
import { IPermission } from 'app/shared/model/permission.model';

export interface IProfile {
  id?: number;
  role?: string;
  users?: IUser[];
  permissions?: IPermission[];
}

export class Profile implements IProfile {
  constructor(public id?: number, public role?: string, public users?: IUser[], public permissions?: IPermission[]) {}
}
