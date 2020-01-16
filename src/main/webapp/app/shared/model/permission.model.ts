import { IProfile } from 'app/shared/model/profile.model';

export interface IPermission {
  id?: number;
  name?: string;
  profiles?: IProfile[];
}

export class Permission implements IPermission {
  constructor(public id?: number, public name?: string, public profiles?: IProfile[]) {}
}
