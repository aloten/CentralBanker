import styled from 'styled-components';

const StyledTable = styled.div`
  table {
    border-collapse: collapse;
    margin: 25px 0;
    font-size: 0.9em;
    /* min-width: 400px; */
    border-radius: 5px 5px 0 0;
    overflow: hidden;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
  }

  thead tr {
    background-color: ${({ theme }) => theme.colors.tertiary};
    color: #ffffff;
    text-align: left;
    font-weight: bold;
  }

  th,
  td {
    padding: 12px 15px;
  }

  tbody tr {
    border-bottom: 1px solid #dddddd;
  }

  tbody tr:nth-of-type(even) {
    background-color: #f3f3f3;
  }

  tbody tr:last-of-type {
    border-bottom: 2px solid #009879;
  }

  tbody tr:hover {
    font-weight: bold;
    color: #009879;
  }
`;

export default StyledTable;
