import CompanyIndex from './CompanyIndex';
import PersonIndex from './PersonIndex';
import SummaryStatistics from './SummaryStatistics';
import styled from 'styled-components';
import ReactModal from 'react-modal';
import { useContext, useState } from 'react';
import PersonDetailModal from './PersonDetailModal';
import Person from '../../interfaces/entities/Person';
import Button from '../../styles/Button';
import { WebSocketContext } from '../../globalState/WebSocketContext';

const StyledDashboard = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  .dashboardItem {
    /* min-width: 100px; */
    flex-grow: 1;
    max-width: 400px;
  }
  .summaryStatistics {
    max-width: 200px;
  }
  .personIndex {
    flex-grow: 2;
  }
`;

const PersonReactModalStyle = {
  content: {
    width: '50%',
    margin: 'auto',
  },
};

const Dashboard = () => {
  const { startStreamingPersonAssets, stopStreamingPersonAssets } =
    useContext(WebSocketContext);

  const [isPersonModalOpen, setIsPersonModalOpen] = useState<boolean>(false);
  const [selectedPersonForModal, setSelectedPersonForModal] =
    useState<Person | null>(null);

  const closePersonModal = () => {
    setIsPersonModalOpen(false);
  };

  // logic for requesting websocket data for person modal
  if (isPersonModalOpen && selectedPersonForModal) {
    console.log('should start streaming person assets');
    startStreamingPersonAssets(
      selectedPersonForModal.financialState.balanceSheet.id
    );
  } else {
    console.log('should stop streaming person assets');
    stopStreamingPersonAssets();
  }

  return (
    <StyledDashboard className='dashboard'>
      <ReactModal
        isOpen={isPersonModalOpen}
        onRequestClose={closePersonModal}
        contentLabel='Person Detail Modal'
        style={PersonReactModalStyle}
        ariaHideApp={false}
      >
        <PersonDetailModal selectedPersonForModal={selectedPersonForModal} />
        <Button className='button' onClick={closePersonModal}>
          Close
        </Button>
      </ReactModal>
      <SummaryStatistics />
      <PersonIndex
        setIsPersonModalOpen={setIsPersonModalOpen}
        setSelectedPersonForModal={setSelectedPersonForModal}
      />
      <CompanyIndex />
    </StyledDashboard>
  );
};

export default Dashboard;
