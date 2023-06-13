import axios from 'axios';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import Rotated2ColumnStyledTable from '../../styles/Rotated2ColumnStyledTable';

const StyledSummaryStatistics = styled(Rotated2ColumnStyledTable)`
  table {
    display: flex;
  }
`;
interface Economy {
  gdp: number;
  moneySupply: number;
}

const fetchEconomyData = async (): Promise<Economy | null> => {
  try {
    const response = await axios.get('/economy');
    console.log(response);
    return response.data;
  } catch (error) {
    console.log('Error fetching economy data: ', error);
    return null;
  }
};

const SummaryStatistics = () => {
  const [economy, setEconomy] = useState<Economy>({ gdp: 0, moneySupply: 0 });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async (): Promise<void> => {
      const data: Economy | null = await fetchEconomyData();
      if (data) {
        setEconomy(data);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <p>Loading, probably error...</p>;
  }

  const headers = ['GDP', 'Money supply'];
  const rowData = [economy.gdp, economy.moneySupply];

  return (
    <StyledSummaryStatistics className='economyIndex'>
      <h3>Economy</h3>
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
    </StyledSummaryStatistics>
  );
};

export default SummaryStatistics;
