import AssetType from './AssetType';
import BalanceSheet from './BalanceSheet';
import EntityModel from './EntityModel';

export default interface Asset extends EntityModel {
  quantity: number;
  balanceSheet: BalanceSheet;
  assetType: AssetType;
  value: number;
}
