import styled from '@emotion/styled';
import Backpack from '~/assets/icons/Backpack.svg?react';
import BeerBottle from '~/assets/icons/BeerBottle.svg?react';
import Buildings from '~/assets/icons/Buildings.svg?react';
import { Tabs } from './Tabs';
import { Tab } from './Tabs/Tab';

export const BasicInfo: React.FC = () => {
  return (
    <>
      <StyledBasicInfo>
        <Tabs>
          <Tab isSelected>상세정보</Tab>
        </Tabs>
        <StyledContentContainer>
          <StyledContent>
            <Backpack />
            <StyledBasicInfoAddress>
              <StyledBasicInfoText>
                서울 용산구 이태원로 216 2층
              </StyledBasicInfoText>
              <StyledBasicInfoText>지번 | 한남동 73-8</StyledBasicInfoText>
            </StyledBasicInfoAddress>
          </StyledContent>
          <StyledContent>
            <BeerBottle />
            <StyledBasicInfoText>매일 | 18:00 ~ 24:00</StyledBasicInfoText>
          </StyledContent>
          <StyledContent>
            <Buildings />
            <StyledBasicInfoText>02-795-5701</StyledBasicInfoText>
          </StyledContent>
        </StyledContentContainer>
      </StyledBasicInfo>
      <StyledButtonWrapper>
        <StyledButton>네이버 맵으로 가기</StyledButton>
      </StyledButtonWrapper>
    </>
  );
};

const StyledBasicInfo = styled.div``;

const StyledContentContainer = styled.div`
  padding: 24px 30px;
  display: flex;
  flex-direction: column;
`;

const StyledContent = styled.div`
  display: flex;
  gap: 19px;
  align-items: center;

  > svg {
    align-self: flex-start;
  }
`;

const StyledBasicInfoText = styled.div`
  height: 32px;
  display: flex;
  align-items: center;
`;

const StyledBasicInfoAddress = styled.div``;

const StyledButtonWrapper = styled.div`
  padding: 0 24px;
`;

const StyledButton = styled.div`
  width: 100%;
  color: #fff;
  background-color: #000000;
  border-radius: 8px;
  padding: 30px 0;
  font-size: 26px;
  font-weight: 500;
  display: flex;
  justify-content: center;
`;
