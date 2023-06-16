import EntityModel from '../../interfaces/entities/EntityModel';

interface TableProps<T> {
  data: T[];
  handleRowDataClick?(id: number): void;
}

const Table = <T extends Record<string, any> & EntityModel>({
  data,
  handleRowDataClick,
}: TableProps<T>) => {
  if (data.length === 0) {
    return <p>No data available.</p>;
  }

  const headers = Object.keys(data[0]);

  const renderObjectOrNull = (value: any, index: number) => {
    if (value === null) {
      return <td key={index}>null</td>;
    }
    return <td key={index}>object</td>;
  };

  const Row = ({ dataObject }: { dataObject: Object }) => {
    return (
      <>
        {Object.values(dataObject).map((value: any, index: number) =>
          typeof value === 'object' ? (
            renderObjectOrNull(value, index)
          ) : (
            <td key={index}>{value}</td>
          )
        )}
      </>
    );
  };

  return (
    <table>
      <thead>
        <tr>
          {headers.map((header) => (
            <th key={header}>{header}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((dataObject, index) => (
          <tr
            key={index}
            onClick={
              handleRowDataClick && (() => handleRowDataClick(dataObject.id))
            }
          >
            <Row dataObject={dataObject} />
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default Table;
