import styled from '@emotion/styled';
import { NavLink } from 'react-router-dom';
import { clickableStyle } from '~/styles/designSystem';

type Props = {
  linkTo: string;
  Icon: React.FC;
  text: string;
};

export const SidebarItem: React.FC<Props> = ({ linkTo, Icon, text }) => {
  return (
    <StyledLink to={linkTo}>
      <Icon />
      {text}
    </StyledLink>
  );
};

const StyledLink = styled(NavLink)`
  width: 100%;
  font-size: 18px;
  padding: 20px 0 20px 40px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: start;
  gap: 14px;
  cursor: pointer;

  ${clickableStyle};
  color: #000000;

  &:link {
    text-decoration: none;
  }

  &.active {
    color: #ff5019;
    border-left: 5px solid #ff5019;
    padding-left: 35px;
    font-weight: bold;
  }
`;
