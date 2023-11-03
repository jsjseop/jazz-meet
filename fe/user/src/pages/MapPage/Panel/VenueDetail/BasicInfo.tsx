import styled from '@emotion/styled';
import Backpack from '~/assets/icons/Backpack.svg?react';
import BeerBottle from '~/assets/icons/BeerBottle.svg?react';
import Buildings from '~/assets/icons/Buildings.svg?react';
import { Tabs } from './Tabs';
import { Tab } from './Tabs/Tab';
import { VenueDetailData } from '~/types/api.types';

type Props = Pick<
  VenueDetailData,
  'roadNameAddress' | 'lotNumberAddress' | 'venueHours' | 'phoneNumber'
> & { naverMapUrl?: string };

export const BasicInfo: React.FC<Props> = ({
  roadNameAddress,
  lotNumberAddress,
  venueHours,
  phoneNumber,
  naverMapUrl,
}) => {
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
              <StyledBasicInfoText>{roadNameAddress}</StyledBasicInfoText>
              <StyledBasicInfoText>{`지번 | ${lotNumberAddress}`}</StyledBasicInfoText>
            </StyledBasicInfoAddress>
          </StyledContent>
          <StyledContent>
            <BeerBottle />
            <div>
              {venueHours.map((venueHour) => (
                <StyledBasicInfoText>{`${venueHour.day} | ${venueHour.businessHours}`}</StyledBasicInfoText>
              ))}
            </div>
          </StyledContent>
          <StyledContent>
            <Buildings />
            <StyledBasicInfoText>{phoneNumber}</StyledBasicInfoText>
          </StyledContent>
        </StyledContentContainer>
      </StyledBasicInfo>
      <StyledButtonWrapper>
        {naverMapUrl && (
          <StyledButton onClick={() => open(naverMapUrl)}>
            네이버 맵으로 가기
          </StyledButton>
        )}
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
