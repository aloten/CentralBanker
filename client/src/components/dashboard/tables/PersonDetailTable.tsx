import Person from '../../../interfaces/entities/Person';

const PersonDetailTable = ({ person }: { person: Person }) => {
  const headers = ['id', 'first name', 'last name', 'job title', 'salary'];
  const row = (person: Person) => {
    return (
      <tr>
        <td key='id'>{person.id}</td>
        <td key='firstName'>{person.firstName}</td>
        <td key='lastName'>{person.lastName}</td>
        <td key='job'>{person.jobTitle}</td>
        <td key='salary'>{person.salary}</td>
      </tr>
    );
  };
  return (
    <table>
      <thead>
        <tr>
          {headers.map((header) => (
            <th key={header}>{header}</th>
          ))}
        </tr>
      </thead>
      <tbody>{row(person)}</tbody>
    </table>
  );
};

export default PersonDetailTable;
