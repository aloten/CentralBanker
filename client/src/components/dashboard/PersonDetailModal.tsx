import axios from 'axios';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Asset from '../../interfaces/entities/Asset';
import Person from '../../interfaces/entities/Person';
import Rotated2ColumnStyledTable from '../../styles/Rotated2ColumnStyledTable';
import StyledTable from '../../styles/StyledTable';
import Table from '../utility/Table';

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
        console.log("person detail modal")
        console.log(data);
      }
    };

    if (selectedPersonForModal) {
      fetchData(selectedPersonForModal);
    }
  }, [selectedPersonForModal]);

  if (selectedPersonForModal === null) {
    return <div>null person</div>;
  }

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
          {/* <RotatedTwoColumnTable headers={headers} rowData={rowData} /> */}
          <StyledTable>
            <Table data={assets} />
          </StyledTable>
        </div>
      ) : null}
    </>
  );
};

export default PersonDetailModal;
