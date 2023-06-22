import ControlPanel from "./ControlPanel";
import Dashboard from "./dashboard/Dashboard";
import styled from "styled-components";

export const StyledHome = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  padding: 2rem;
`

const Home = () => {
return (
  <StyledHome className="home">
    Home
    <Dashboard />   
    <ControlPanel />
  </StyledHome>
)
}

export default Home;