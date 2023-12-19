import styled from '@emotion/styled';
import CommentIcon from '@mui/icons-material/Comment';
import CommentOutlinedIcon from '@mui/icons-material/CommentOutlined';
import HomeIcon from '@mui/icons-material/Home';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import MapIcon from '@mui/icons-material/Map';
import MapOutlinedIcon from '@mui/icons-material/MapOutlined';
import PianoIcon from '@mui/icons-material/Piano';
import PianoTwoToneIcon from '@mui/icons-material/PianoTwoTone';
import { NavLink } from 'react-router-dom';
import { BOTTOM_NAVIGATION_Z_INDEX } from '~/constants/Z_INDEX';

export const BottomNavigation: React.FC = () => {
  return (
    <StyledBottomNavigation>
      <NavLink to="/">
        {({ isActive }) => (isActive ? <HomeIcon /> : <HomeOutlinedIcon />)}
      </NavLink>
      <NavLink to="/map">
        {({ isActive }) => (isActive ? <MapIcon /> : <MapOutlinedIcon />)}
      </NavLink>
      <NavLink to="/show">
        {({ isActive }) => (isActive ? <PianoTwoToneIcon /> : <PianoIcon />)}
      </NavLink>
      <NavLink to="/inquiry">
        {({ isActive }) =>
          isActive ? <CommentIcon /> : <CommentOutlinedIcon />
        }
      </NavLink>
    </StyledBottomNavigation>
  );
};

const StyledBottomNavigation = styled.nav`
  width: 100%;
  height: 60px;
  box-sizing: border-box;
  background-color: #000;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  gap: 16px;
  position: fixed;
  bottom: 0;
  left: 0;
  z-index: ${BOTTOM_NAVIGATION_Z_INDEX};

  a {
    color: #fff;
    text-decoration: none;
  }
`;
