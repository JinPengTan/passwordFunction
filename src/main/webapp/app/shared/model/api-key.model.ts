import { Moment } from 'moment';

export interface IApiKey {
  id?: number;
  requestorType?: string;
  requestorId?: string;
  apiKey?: string;
  apiStatus?: string;
  modifiedDate?: Moment;
  createdDate?: Moment;
}

export class ApiKey implements IApiKey {
  constructor(
    public id?: number,
    public requestorType?: string,
    public requestorId?: string,
    public apiKey?: string,
    public apiStatus?: string,
    public modifiedDate?: Moment,
    public createdDate?: Moment
  ) {}
}
