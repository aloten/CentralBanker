import AssetType from './AssetType';
import EntityModel from './EntityModel';

export default interface Asset extends EntityModel {
  assetType: AssetType;
  value: number;
}
