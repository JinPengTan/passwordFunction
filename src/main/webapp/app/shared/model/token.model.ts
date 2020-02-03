export interface IToken {
  id?: number;
  walletId?: string;
  tpan?: string;
  hashKey?: string;
  tokenExpiry?: string;
  tokenStatus?: string;
  maskPan?: string;
  enPan?: string;
  expiryDate?: string;
  version?: number;
}

export class Token implements IToken {
  constructor(
    public id?: number,
    public walletId?: string,
    public tpan?: string,
    public hashKey?: string,
    public tokenExpiry?: string,
    public tokenStatus?: string,
    public maskPan?: string,
    public enPan?: string,
    public expiryDate?: string,
    public version?: number
  ) {}
}
