import { useState, useEffect, createContext, useContext } from 'react';
import Person from '../interfaces/entities/Person';

function usePeopleSource(): { people: Person[] } {
  const [people, setPeople] = useState<Person[]>([]);

  useEffect(() => {
    fetch('/person')
      .then((response) => response.json())
      .then((data) => setPeople(data));
  }, []);

  return { people };
}

const PeopleContext = createContext<ReturnType<typeof usePeopleSource>>({
  people: [] as Person[],
});

export function usePeople() {
  return useContext(PeopleContext);
}

export const PeopleProvider = ({ children }: { children: React.ReactNode }) => {
  return (
    <PeopleContext.Provider value={usePeopleSource()}>
      {children}
    </PeopleContext.Provider>
  );
};
