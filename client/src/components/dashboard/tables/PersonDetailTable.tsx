import Person from "../../../interfaces/entities/Person";

const PersonDetailTable = ({ person }: { person: Person }) => {
  const headers = ['id', 'first name', 'last name', 'job title', 'salary'];
  const row = (person: Person) => {
    return (
      <tr>
        <td>{person.id}</td>
        <td>{person.firstName}</td>
        <td>{person.lastName}</td>
        <td>{person.jobTitle}</td>
        <td>{person.salary}</td>
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
