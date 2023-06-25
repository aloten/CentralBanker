import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Asset from '../../interfaces/entities/Asset';
import Person from '../../interfaces/entities/Person';
import Rotated2ColumnStyledTable from '../../styles/Rotated2ColumnStyledTable';
import AssetTable from './tables/AssetTable';
import PersonDetailTable from './tables/PersonDetailTable';
import StyledTable from '../../styles/StyledTable';
import { API_PROXY } from '../../backendInfo';

interface PersonDetailModalProps {
  selectedPersonForModal: Person | null;
}

const StyledMainAttributesTable = styled(Rotated2ColumnStyledTable)``;

const PersonDetailModal = ({
  selectedPersonForModal,
}: PersonDetailModalProps) => {
  const [assets, setAssets] = useState<Asset[]>([]);

  const createAssetSseConnection = (balanceSheetId: number): EventSource => {
    console.log('create Sse connection assets');
    const endpoint = `${API_PROXY}/sse/assets?balanceSheetId=${balanceSheetId}`;
    const eventSource = new EventSource(endpoint);
    eventSource.addEventListener('ASSETS', (event) => {
      setAssets(JSON.parse(event.data));
      // console.log(JSON.parse(event.data));
    });
    return eventSource;
  };

  useEffect(() => {
    let eventSource: EventSource;
    if (selectedPersonForModal) {
      eventSource = createAssetSseConnection(
        selectedPersonForModal.financialState.balanceSheet.id
      );
    }

    return () => {
      if (eventSource) {
        eventSource.close();
      }
      console.log('personDetailModal useEffect() unmounting return function');
    };
  }, [selectedPersonForModal]);

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
          <StyledTable>
            <AssetTable assets={assets} />
          </StyledTable>
        </div>
      ) : null}
    </>
  );
};

export default PersonDetailModal;
