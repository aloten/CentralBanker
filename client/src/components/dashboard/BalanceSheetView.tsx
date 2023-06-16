import styled from 'styled-components';
import BalanceSheet from '../../interfaces/entities/BalanceSheet';

const StyledBalanceSheetView = styled.div``;
interface BalanceSheetViewProps {
  balanceSheet: BalanceSheet;
}

const BalanceSheetView = ({ balanceSheet }: BalanceSheetViewProps) => {
  return (
    <StyledBalanceSheetView>
      <h3>Assets</h3>
    </StyledBalanceSheetView>
  );
};

export default BalanceSheetView;
