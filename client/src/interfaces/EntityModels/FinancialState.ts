import BalanceSheet from './BalanceSheet';
import EntityModel from './EntityModel';

export default interface FinancialState extends EntityModel {
  balanceSheet: BalanceSheet;
}
