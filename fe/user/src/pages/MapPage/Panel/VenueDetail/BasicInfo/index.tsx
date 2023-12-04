import { css } from '@emotion/react';
import styled from '@emotion/styled';
import ContentCopyRoundedIcon from '@mui/icons-material/ContentCopyRounded';
import Backpack from '~/assets/icons/Backpack.svg?react';
import BeerBottle from '~/assets/icons/BeerBottle.svg?react';
import Buildings from '~/assets/icons/Buildings.svg?react';
import { VenueDetailData } from '~/types/api.types';
import { Tabs } from '../Tabs';
import { Tab } from '../Tabs/Tab';
import { VenueHours } from './VenueHours';

type Props = Pick<
  VenueDetailData,
  'id' | 'roadNameAddress' | 'lotNumberAddress' | 'venueHours' | 'phoneNumber'
> & { naverMapUrl?: string };

export const BasicInfo: React.FC<Props> = ({
  id: venueId,
  roadNameAddress,
  lotNumberAddress,
  venueHours,
  phoneNumber,
  naverMapUrl,
}) => {
  const copyToClipboard = (text: string) => {
    navigator.clipboard
      .writeText(text)
      .then(() => alert('클립보드에 복사되었습니다.'));
  };

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
                {roadNameAddress}
                <StyledCopyButton
                  onClick={() => copyToClipboard(roadNameAddress)}
                >
                  <ContentCopyRoundedIcon />
                  복사
                </StyledCopyButton>
              </StyledBasicInfoText>
              <StyledBasicInfoText>{`지번 | ${lotNumberAddress}`}</StyledBasicInfoText>
            </StyledBasicInfoAddress>
          </StyledContent>
          <StyledContent>
            <BeerBottle />
            <VenueHours key={venueId} venueHours={venueHours} />
          </StyledContent>
          <StyledContent>
            <Buildings />
            <StyledBasicInfoText>
              {phoneNumber}
              <StyledCopyButton onClick={() => copyToClipboard(phoneNumber)}>
                <ContentCopyRoundedIcon />
                복사
              </StyledCopyButton>
            </StyledBasicInfoText>
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

const buttonStyle = css`
  &:hover {
    cursor: pointer;
    opacity: 0.7;
  }

  &:active {
    opacity: 0.5;
  }
`;

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

  ${buttonStyle}
`;

const StyledCopyButton = styled.button`
  padding: 0 4px;
  display: flex;
  align-items: center;
  color: #60bf48;
  font-size: 14px;

  ${buttonStyle}
`;
