import { IUser } from 'app/core/user/user.model';
import { IProfile } from 'app/shared/model/profile.model';

export interface IExtendUser {
  id?: number;
  user?: IUser;
  profile?: IProfile;
}

export class ExtendUser implements IExtendUser {
  constructor(public id?: number, public user?: IUser, public profile?: IProfile) {}
}
