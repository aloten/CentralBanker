import EntityModel from "./EntityModel";
import FinancialState from "./FinancialState";

interface Person extends EntityModel {
  firstName: string;
  lastName: string;
  salary: number;
  // other attributes
  financialState: FinancialState;
}

export default Person;
