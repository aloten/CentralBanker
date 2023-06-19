import axios from 'axios';
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Asset from '../../interfaces/entities/Asset';
import Person from '../../interfaces/entities/Person';
import Rotated2ColumnStyledTable from '../../styles/Rotated2ColumnStyledTable';
import AssetTable from './tables/AssetTable';
import PersonDetailTable from './tables/PersonDetailTable';
import StyledTable from '../../styles/StyledTable';

interface PersonDetailModalProps {
  selectedPersonForModal: Person | null;
}

const StyledMainAttributesTable = styled(Rotated2ColumnStyledTable)``;

const fetchAssetData = async (
  balanceSheetId: number
): Promise<Asset[] | null> => {
  try {
    const response = await axios.get(
      `/person/assets?balanceSheetId=${balanceSheetId}`
    );
    return response.data;
  } catch (error) {
    console.log('Error fetching people data: ', error);
    return null;
  }
};

const PersonDetailModal = ({
  selectedPersonForModal,
}: PersonDetailModalProps) => {
  const [assets, setAssets] = useState<Asset[]>([]);

  useEffect(() => {
    const fetchData = async (person: Person): Promise<void> => {
      const data: Asset[] | null = await fetchAssetData(
        person.financialState.balanceSheet.id
      );
      if (data) {
        setAssets(data);
      }
    };

    if (selectedPersonForModal) {
      fetchData(selectedPersonForModal);
    }
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
