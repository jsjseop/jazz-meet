import styled from '@emotion/styled';
import InstagramIcon from '@mui/icons-material/Instagram';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';

export const Header: React.FC = () => {
  return (
    <StyledHeader>
      <StyledTitleContainer>
        <StyledTitle>올댓재즈</StyledTitle>
        <StyledSubTitle>All That Jazz</StyledSubTitle>
      </StyledTitleContainer>
      <StyledButtons>
        <IconButton>
          <InstagramIcon
            sx={{ width: '29px', height: '29px', fill: 'white' }}
          />
        </IconButton>
        <IconButton>
          <HomeOutlinedIcon
            sx={{ width: '29px', height: '29px', fill: 'white' }}
          />
        </IconButton>
        <IconButton isMoreButton>
          <MoreHorizIcon
            sx={{ width: '29px', height: '29px', fill: '#6B6B6B' }}
          />
        </IconButton>
      </StyledButtons>
    </StyledHeader>
  );
};

const StyledHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

const StyledTitleContainer = styled.div`
  display: flex;
  gap: 10px;
`;

const StyledTitle = styled.div``;

const StyledSubTitle = styled.div``;

const StyledButtons = styled.div`
  display: flex;
  gap: 10px;
`;

const IconButton = styled.div<{ isMoreButton?: boolean }>`
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background-color: #000;
  ${({ isMoreButton }) => isMoreButton && `background-color: #d9d9d9;`}
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;
