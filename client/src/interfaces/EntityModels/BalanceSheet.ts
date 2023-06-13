import Asset from "./Asset";
import EntityModel from "./EntityModel";

export default interface BalanceSheet extends EntityModel {
  assets: Asset[];
}