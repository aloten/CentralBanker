import { useContext } from 'react';
import styled from 'styled-components';
import { WebSocketContext } from '../../globalState/WebSocketContext';
import Person from '../../interfaces/entities/Person';
import Rotated2ColumnStyledTable from '../../styles/Rotated2ColumnStyledTable';
import StyledTable from '../../styles/StyledTable';
import AssetTable from './tables/AssetTable';
import PersonDetailTable from './tables/PersonDetailTable';

interface PersonDetailModalProps {
  selectedPersonForModal: Person | null;
}

// maybe want to use this to style a 2 column table for main person details
const StyledMainAttributesTable = styled(Rotated2ColumnStyledTable)``;

const PersonDetailModal = ({
  selectedPersonForModal,
}: PersonDetailModalProps) => {
  const { personAssets } = useContext(WebSocketContext);

  if (selectedPersonForModal === null) {
    return <div>null person</div>;
  }

  return (
    <>
      {selectedPersonForModal ? (
        <div>
          <h2>Person Details</h2>
          <StyledTable>
            <PersonDetailTable person={selectedPersonForModal} />
          </StyledTable>
          <h2>Assets</h2>
          {personAssets && (
            <StyledTable>
              <AssetTable assets={personAssets} />
            </StyledTable>
          )}
        </div>
      ) : null}
    </>
  );
};

export default PersonDetailModal;
