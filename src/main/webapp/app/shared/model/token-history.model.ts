import { Moment } from 'moment';

export interface ITokenHistory {
  id?: number;
  tpan?: string;
  hashKey?: string;
  tokenExpiry?: string;
  tokenStatus?: string;
  maskPan?: string;
  enPan?: string;
  expiryDate?: string;
  trId?: string;
  version?: number;
  historyDt?: Moment;
}

export class TokenHistory implements ITokenHistory {
  constructor(
    public id?: number,
    public tpan?: string,
    public hashKey?: string,
    public tokenExpiry?: string,
    public tokenStatus?: string,
    public maskPan?: string,
    public enPan?: string,
    public expiryDate?: string,
    public trId?: string,
    public version?: number,
    public historyDt?: Moment
  ) {}
}
