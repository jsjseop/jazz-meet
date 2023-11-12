import styled from '@emotion/styled';
import CaretRight from '~/assets/icons/CaretRight.svg?react';

export const VenueCard: React.FC = () => {
  return (
    <StyledVenueCard>
      <StyledCardHeader>
        <span>공연장이름</span>
        <CaretRight />
      </StyledCardHeader>
      <StyledShowInfo>
        <span>20:30 - 22:30</span>
        <span>ooo oooo 트리오</span>
      </StyledShowInfo>
      <StyledShowInfo>
        <span>20:30 - 22:30</span>
        <span>ooo oooo 트리오</span>
      </StyledShowInfo>
    </StyledVenueCard>
  );
};

const StyledVenueCard = styled.div`
  border-radius: 6px;
  border: 1px solid #dbe1e4;
  padding: 12px 0;
`;

const StyledCardHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 20px;
  font-size: 24px;
  font-weight: bold;
  color: #1b1b1b;
`;

const StyledShowInfo = styled.div`
  display: flex;
  align-items: center;
  gap: 42px;
  padding: 7px 20px;
  font-size: 20px;
  font-weight: 500;

  > span:first-of-type {
    color: #47484e;
  }

  > span:last-of-type {
    color: #686970;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }
`;
