import styled from 'styled-components';
import BalanceSheet from '../../interfaces/EntityModels/BalanceSheet';
import Table from '../utility/Table';
import Asset from '../../interfaces/EntityModels/Asset';
import StyledTable from '../../styles/StyledTable';

const StyledBalanceSheetView = styled.div``;
interface BalanceSheetViewProps {
  balanceSheet: BalanceSheet;
}

const BalanceSheetView = ({ balanceSheet }: BalanceSheetViewProps) => {
  return (
    <StyledBalanceSheetView>
      <h3>Assets</h3>
      <StyledTable>
        <Table<Asset> data={balanceSheet.assets} />
      </StyledTable>
    </StyledBalanceSheetView>
  );
};

export default BalanceSheetView;
