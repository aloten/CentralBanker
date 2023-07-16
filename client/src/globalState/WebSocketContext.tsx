import {
  ReactNode,
  createContext,
  useState,
  useEffect,
  useCallback,
} from 'react';
import Asset from '../interfaces/entities/Asset';
import { Client, IMessage } from '@stomp/stompjs';
import { WEBSOCKET_URL } from '../backendInfo';

const WebSocketContext = createContext<WebSocketContextProps>({
  personAssets: null,
  updatePersonAssets: () => {},
  startStreamingPersonAssets: () => {},
  stopStreamingPersonAssets: () => {},
});

interface WebSocketContextProps {
  personAssets: Asset[] | null;
  updatePersonAssets: (assets: Asset[]) => void;
  startStreamingPersonAssets: (balanceSheetId: number) => void;
  stopStreamingPersonAssets: () => void;
}

const WebSocketProvider = ({ children }: { children: ReactNode }) => {
  const [client] = useState(
    new Client({
      brokerURL: WEBSOCKET_URL,
      onConnect: () => {
        client.subscribe('/topic/personAssets', (message) =>
          handlePersonAssetsMessage(message)
        );
      },
      onDisconnect: () => {
        console.log('disconnected stomp client');
      },
    })
  );

  if (!client.connected) {
    console.log('stomp client not connected');
  } else {
    console.log('stomp client connected');
  }

  useEffect(() => {
    client.activate();
    console.log('stomp client activated on mount');

    return () => {
      client.deactivate();
      console.log('STOMP client unmounting and deactivating');
    };
  }, [client]);

  const [personAssets, setPersonAssets] = useState<Asset[] | null>(null);

  const updatePersonAssets = (assets: Asset[]) => {
    setPersonAssets(assets);
  };

  const startStreamingPersonAssets = useCallback(
    (balanceSheetId: number) => {
      if (client.connected) {
        client.publish({
          destination: `/app/personAssets/${balanceSheetId}`,
        });
      }
    },
    [client]
  );

  const stopStreamingPersonAssets = useCallback(() => {
    console.log('inside stopStreaming...');
    console.log(client);
    if (client.connected) {
      console.log('client is connected');
      client.publish({
        destination: '/app/personAssets/stop',
      });
    }
  }, [client]);

  const handlePersonAssetsMessage = (message: IMessage) => {
    console.log(`Received messaged from 'personAssets' topic: ${message.body}`);
    setPersonAssets(JSON.parse(message.body));
  };

  return (
    <WebSocketContext.Provider
      value={{
        personAssets,
        updatePersonAssets,
        startStreamingPersonAssets,
        stopStreamingPersonAssets,
      }}
    >
      {children}
    </WebSocketContext.Provider>
  );
};

export { WebSocketContext, WebSocketProvider };
