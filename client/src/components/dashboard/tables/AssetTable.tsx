import Asset from '../../../interfaces/entities/Asset';

const AssetTable = ({ assets }: { assets: Asset[] }) => {
  const headers = ['id', 'asset type name', 'quantity'];
  const row = (asset: Asset) => {
    return (
      <tr key={asset.id}>
        <td key='id'>{asset.id}</td>
        <td key='assetTypeName'>{asset.assetType.name}</td>
        <td key='quantity'>{asset.quantity}</td>
      </tr>
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
      <tbody>{assets.map((asset) => row(asset))}</tbody>
    </table>
  );
};

export default AssetTable;
