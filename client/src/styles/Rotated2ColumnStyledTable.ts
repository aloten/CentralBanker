import styled from 'styled-components';

const Rotated2ColumnStyledTable = styled.div`
  table {
    width: 100%;
    border-collapse: collapse;
  }

  table thead th {
    background-color: ${({ theme }) => theme.colors.secondary};
    color: ${({ theme }) => theme.colors.quaternary};
    font-weight: bold;
    padding: 8px;
    text-align: left;
  }

  table tbody td {
    padding: 8px;
  }

  table tbody tr:nth-child(even) {
    background-color: ${({ theme }) => theme.colors.secondary};
    color: ${({ theme }) => theme.colors.quaternary};
  }

  table tbody td:first-child,
  table thead th:first-child {
    padding-left: 12px;
  }

  table tbody td:last-child,
  table thead th:last-child {
    padding-right: 12px;
  }
`;

export default Rotated2ColumnStyledTable;