import styled from '@emotion/styled';
import RoomRoundedIcon from '@mui/icons-material/RoomRounded';
import { useNavigate } from 'react-router-dom';
import { SearchSuggestion } from '~/types/api.types';

type Props = {
  suggestion: SearchSuggestion;
  onClose: () => void;
};

export const SuggestionItem: React.FC<Props> = ({ suggestion, onClose }) => {
  const navigate = useNavigate();

  const navigateToVenueDetail = () => {
    navigate(`/map/venues/${suggestion.id}`);
    onClose();
  };

  return (
    <StyledSuggestionItem onClick={navigateToVenueDetail}>
      <RoomRoundedIcon />
      <StyledInformation>
        <h4>{suggestion.name}</h4>
        <p>{suggestion.address}</p>
      </StyledInformation>
    </StyledSuggestionItem>
  );
};

const StyledSuggestionItem = styled.li`
  padding: 12px 16px;
  display: flex;
  gap: 8px;

  &:hover,
  &:focus {
    cursor: pointer;
    background-color: #f5f5f5;
  }
`;

const StyledInformation = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 8px;

  > h4 {
    font-size: 16px;
    font-weight: 600;
  }

  > p {
    font-size: 14px;
    color: #757575;
  }
`;
