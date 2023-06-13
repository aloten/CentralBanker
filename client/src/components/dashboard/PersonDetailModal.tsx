import Person from '../../interfaces/EntityModels/Person';
import Rotated2ColumnStyledTable from '../../styles/Rotated2ColumnStyledTable';
import BalanceSheetView from './BalanceSheetView';
import styled from 'styled-components';

interface PersonDetailModalProps {
  selectedPersonForModal: Person | null;
}

const StyledMainAttributesTable = styled(Rotated2ColumnStyledTable)`
  
`

const PersonDetailModal = ({
  selectedPersonForModal,
}: PersonDetailModalProps) => {
  if (selectedPersonForModal === null) {
    return <div>null person</div>;
  }

  console.log(selectedPersonForModal);

  const RotatedTwoColumnTable = ({
    headers,
    rowData,
  }: {
    headers: string[];
    rowData: string[];
  }) => {
    return (
      <Rotated2ColumnStyledTable>
        <table>
          <tbody>
            {headers.map((header, index) => (
              <tr key={index}>
                <td>{header}</td>
                <td>{rowData[index]}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </Rotated2ColumnStyledTable>
    );
  };

  const headers = ['ID', 'First name', 'Last name', 'Salary'];
  const rowData = Object.values(selectedPersonForModal);

  return (
    <>
      {selectedPersonForModal ? (
        <div>
          <h2>Person Details</h2>
          <RotatedTwoColumnTable headers={headers} rowData={rowData} />
          <BalanceSheetView
            balanceSheet={selectedPersonForModal.financialState.balanceSheet}
          />
        </div>
      ) : null}
    </>
  );
};

export default PersonDetailModal;
