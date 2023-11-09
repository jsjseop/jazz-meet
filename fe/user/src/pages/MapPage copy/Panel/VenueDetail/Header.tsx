import styled from '@emotion/styled';
import InstagramIcon from '@mui/icons-material/Instagram';
import HomeOutlinedIcon from '@mui/icons-material/HomeOutlined';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import { VenueDetailData } from '~/types/api.types';
import Blog from '~/assets/icons/Blog.svg?react';
import React from 'react';

type Props = Pick<VenueDetailData, 'name' | 'links'>;

export const Header: React.FC<Props> = ({ name, links }) => {
  return (
    <StyledHeader>
      <StyledTitleContainer>
        <StyledTitle>{name}</StyledTitle>
        {/* <StyledSubTitle>All That Jazz</StyledSubTitle> */}
      </StyledTitleContainer>
      <StyledButtons>
        {links.map((link) => (
          <React.Fragment key={link.type}>{renderButton(link)}</React.Fragment>
        ))}
        <IconButton>
          <MoreHorizIcon
            style={{ width: '29px', height: '29px', fill: 'white' }}
          />
        </IconButton>
      </StyledButtons>
    </StyledHeader>
  );
};

const renderButton = (link: VenueDetailData['links'][0]) => {
  switch (link.type) {
    case 'naverMap':
      return <></>;
    case 'instagram':
      return (
        <IconButton onClick={() => open(link.url)}>
          <InstagramIcon
            sx={{ width: '29px', height: '29px', fill: 'white' }}
          />
        </IconButton>
      );
    case 'blog':
      return (
        <IconButton onClick={() => open(link.url)}>
          <Blog style={{ width: '29px', height: '29px', fill: 'white' }} />
        </IconButton>
      );
    case 'official':
      return (
        <IconButton onClick={() => open(link.url)}>
          <HomeOutlinedIcon
            style={{ width: '29px', height: '29px', fill: 'white' }}
          />
        </IconButton>
      );
    default:
      return <></>;
  }
};

const StyledHeader = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 26px 44px;
`;

const StyledTitleContainer = styled.div`
  display: flex;
  gap: 10px;
  align-items: baseline;
`;

const StyledTitle = styled.div`
  font-size: 36px;
  font-weight: bolder;
`;

// const StyledSubTitle = styled.div`
//   font-size: 26px;
//   font-weight: medium;
//   color: #6f6f6f;
// `;

const StyledButtons = styled.div`
  display: flex;
  gap: 10px;
`;

const IconButton = styled.div<{ isMoreButton?: boolean }>`
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background-color: #000;
  ${({ isMoreButton }) => isMoreButton && `background-color: #d9d9d9;`};
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
`;
