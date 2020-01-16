import { IPermission } from 'app/shared/model/permission.model';
import { IExtendUser } from 'app/shared/model/extend-user.model';

export interface IProfile {
  id?: number;
  role?: string;
  permissions?: IPermission[];
  extendUsers?: IExtendUser[];
}

export class Profile implements IProfile {
  constructor(public id?: number, public role?: string, public permissions?: IPermission[], public extendUsers?: IExtendUser[]) {}
}
