import styled from 'styled-components';

export const StyledHeader = styled.header`
  font-size: 2rem;
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
  height: 4rem;
  background-color: ${({ theme }) => theme.colors.tertiary};
  color: white;
`;

const Header = () => {
  return <StyledHeader className='header'>Central Banker</StyledHeader>;
};

export default Header;
