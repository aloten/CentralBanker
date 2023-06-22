import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPause, faPlay } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import axios from 'axios';

const StyledControlPanel = styled.div`
  background-color: ${({ theme }) => theme.colors.tertiary};
  color: white;
  padding: 1rem 3rem;
  position: fixed;
  bottom: 20px;
  right: auto;
  z-index: 9999;
  text-align: center;
  /* font-size: 3rem; */
  border-radius: 5px;

  .pausePlayIcon:hover {
    color: #cccccc;
  }
`;

const sendPauseEnginePost = async (pauseGame: boolean) => {
  const route = pauseGame ? 'pause' : 'play';

  try {
    const response = await axios.post(`engine/${route}`);
    console.log('sendPauseEnginePost res status: ', response.status);
  } catch (error) {
    console.log('Error sending pause engine post: ', error);
  }
};

const ControlPanel = () => {
  const [isGamePaused, setIsGamePaused] = useState<boolean>(false);

  const handlePausePlayClick = () => {
    sendPauseEnginePost(!isGamePaused);
    setIsGamePaused(!isGamePaused);
  };

  return (
    <StyledControlPanel onClick={handlePausePlayClick}>
      <FontAwesomeIcon
        className='pausePlayIcon'
        icon={isGamePaused ? faPlay : faPause}
        size='3x'
      />
    </StyledControlPanel>
  );
};

export default ControlPanel;
