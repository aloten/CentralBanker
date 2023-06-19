import EntityModel from "./EntityModel";
import FinancialState from "./FinancialState";

interface Person extends EntityModel {
  firstName: string;
  lastName: string;
  salary: number;
  jobTitle: string;
  financialState: FinancialState;
}

export default Person;
