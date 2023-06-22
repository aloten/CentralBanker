import Header from './components/Header';
import styled from 'styled-components';
import { ThemeProvider } from 'styled-components';
import theme from './Theme';
import { PeopleProvider } from './globalState/personStore';
import Home from './components/Home';
import { AssetsProvider } from './globalState/assetStore';
import { useEffect } from 'react';

const StyledApp = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  /* background-color: ${theme.colors.primary};
  color: white; */
  height: 100%;
`;

function App() {
  /**
   * test sse 
   * 
   * 
const eventSourceURL = 'http://localhost:8080/api/v1/sse';
  useEffect(() => {
    console.log("sse")
    const source = new EventSource(eventSourceURL);
    source.addEventListener("people", (event) => {
      console.log(event.data)
    })
  }, []);
   * 
   */

  return (
    <ThemeProvider theme={theme}>
      <PeopleProvider>
        <AssetsProvider>
          <StyledApp className='App'>
            <Header />
            <Home />
          </StyledApp>
        </AssetsProvider>
      </PeopleProvider>
    </ThemeProvider>
  );
}

export default App;
