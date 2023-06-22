import { useState, useEffect, createContext, useContext } from 'react';
import Asset from '../interfaces/entities/Asset';

function useAssetSource(): { assets: Asset[] } {
  const [assets, setAssets] = useState<Asset[]>([]);

  useEffect(() => {
    // incorrect route, needs balancesheet id
    fetch('/person/assets')
      .then((response) => response.json())
      .then((data) => setAssets(data));
  }, []);

  return { assets };
}

const AssetsContext = createContext<ReturnType<typeof useAssetSource>>({
  assets: [] as Asset[],
});

export function useAssets() {
  return useContext(AssetsContext);
}

export const AssetsProvider = ({ children }: { children: React.ReactNode }) => {
  return (
    <AssetsContext.Provider value={useAssetSource()}>
      {children}
    </AssetsContext.Provider>
  );
};
