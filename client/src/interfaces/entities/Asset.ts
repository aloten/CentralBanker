import AssetType from './AssetType';
import BalanceSheet from './BalanceSheet';
import EntityModel from './EntityModel';

export default interface Asset extends EntityModel {
  balanceSheet: BalanceSheet;
  assetType: AssetType;
  value: number;
}
