import axios from 'axios';
import { useState, useEffect } from 'react';
import styled from 'styled-components';
import StyledTable from '../../styles/StyledTable';
import Table from '../utility/Table';
import TruncatedPerson from '../../interfaces/TruncatedPerson';
import Person from '../../interfaces/entities/Person';
import StyledTable2 from '../../styles/StyledTable';

const StyledPersonIndex = styled(StyledTable2)``;

interface PersonIndexProps {
  setIsPersonModalOpen: React.Dispatch<React.SetStateAction<boolean>>;
  setSelectedPersonForModal: React.Dispatch<
    React.SetStateAction<Person | null>
  >;
}

const fetchPeopleData = async (): Promise<Person[] | null> => {
  try {
    const response = await axios.get('/person');
    return response.data;
  } catch (error) {
    console.log('Error fetching people data: ', error);
    return null;
  }
};

const PersonIndex = ({
  setIsPersonModalOpen,
  setSelectedPersonForModal,
}: PersonIndexProps) => {
  const [loading, setLoading] = useState(true);
  const [people, setPeople] = useState<Person[]>([]);

  useEffect(() => {
    const fetchData = async (): Promise<void> => {
      const data: Person[] | null = await fetchPeopleData();
      if (data) {
        setPeople(data);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handlePersonRowClick = (id: number) => {
    if (id === 0) {
      setSelectedPersonForModal(null);
      setIsPersonModalOpen(false);
    }
    const person: Person | undefined = people.find(
      (person) => person.id === id
    );
    setSelectedPersonForModal(person ? person : null);
    setIsPersonModalOpen(true);
  };

  const peopleWithTruncatedAttributes: TruncatedPerson[] = people.map(
    ({ id, firstName, lastName, salary }) => ({
      id,
      firstName,
      lastName,
      salary,
    })
  );

  if (loading) {
    return <p>Loading, probably error...</p>;
  }

  return (
    <StyledPersonIndex className='dashboardItem personIndex'>
      <h3>People</h3>
      {people ? (
        <Table<TruncatedPerson>
          data={peopleWithTruncatedAttributes}
          handleRowDataClick={handlePersonRowClick}
        />
      ) : null}
    </StyledPersonIndex>
  );
};

export default PersonIndex;
