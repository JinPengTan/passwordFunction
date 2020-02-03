export interface IUniqueToken {
  id?: number;
  walletId?: string;
  tpan?: string;
}

export class UniqueToken implements IUniqueToken {
  constructor(public id?: number, public walletId?: string, public tpan?: string) {}
}
