import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ICycle {
  id?: number;
  cycleCount?: number;
  cycleDatetime?: Moment;
  cyclePassword?: string;
  user?: IUser;
}

export class Cycle implements ICycle {
  constructor(
    public id?: number,
    public cycleCount?: number,
    public cycleDatetime?: Moment,
    public cyclePassword?: string,
    public user?: IUser
  ) {}
}
