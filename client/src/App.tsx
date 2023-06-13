import Home from './components/Home';
import Header from './components/Header';
import styled from 'styled-components';
import { ThemeProvider } from 'styled-components';

const theme = {
  colors: {
    primary: '#222831',
    secondary: '#393E46',
    tertiary: '#00ADB5',
    quarnary: '#EEEEEE',
  },
};

const StyledApp = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: ${theme.colors.primary};
  color: white;
  height: 100%; 
`;

function App() {
  return (
    <ThemeProvider theme={theme}>
      <StyledApp className='App'>
        <Header />
        <Home />
      </StyledApp>
    </ThemeProvider>
  );
}

export default App;
