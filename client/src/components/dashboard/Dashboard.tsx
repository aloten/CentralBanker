import CompanyIndex from './CompanyIndex';
import PersonIndex from './PersonIndex';
import SummaryStatistics from './SummaryStatistics';
import styled from 'styled-components';
import ReactModal from 'react-modal';
import { useState } from 'react';
import PersonDetailModal from './PersonDetailModal';
import Person from '../../interfaces/EntityModels/Person';

const StyledDashboard = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

const Dashboard = () => {
  const [isPersonModalOpen, setIsPersonModalOpen] = useState<boolean>(false);
  const [selectedPersonForModal, setSelectedPersonForModal] =
    useState<Person | null>(null);

  const closePersonModal = () => {
    setIsPersonModalOpen(false);
  };

  const PersonReactModalStyle = {
    content: {
      width: '50%',
      margin: 'auto'
    },
  };

  return (
    <StyledDashboard className='dashboard'>
      <ReactModal
        isOpen={isPersonModalOpen}
        onRequestClose={closePersonModal}
        contentLabel='Person Detail Modal'
        style={PersonReactModalStyle}
      >
        <PersonDetailModal selectedPersonForModal={selectedPersonForModal} />
        <button onClick={closePersonModal}>Close</button>
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
