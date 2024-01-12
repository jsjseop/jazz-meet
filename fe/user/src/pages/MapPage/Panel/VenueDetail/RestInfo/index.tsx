import styled from '@emotion/styled';
import LocalCafeOutlinedIcon from '@mui/icons-material/LocalCafeOutlined';
import { VenueDetailData } from '~/types/api.types';
import { Tabs } from '../Tabs';
import { Tab } from '../Tabs/Tab';
import { ShowInfo } from './ShowInfo';

type Props = Pick<VenueDetailData, 'description'>;

export const RestInfo: React.FC<Props> = ({ description }) => {
  return (
    <StyledRestInfo>
      <Tabs>
        <Tab isSelected>공연정보</Tab>
      </Tabs>

      <StyledRestInfoContent>
        <ShowInfo />
      </StyledRestInfoContent>

      <Tabs>
        <Tab isSelected>공연장정보</Tab>
      </Tabs>
      <StyledContentContainer>
        <StyledContent>
          <LocalCafeOutlinedIcon
            sx={{ width: '28px', height: '28px', fill: '#848484' }}
          />
          <StyledBasicInfoText>{description}</StyledBasicInfoText>
        </StyledContent>
      </StyledContentContainer>
    </StyledRestInfo>
  );
};

const StyledRestInfo = styled.div`
  padding: 30px 0;
`;

const StyledRestInfoContent = styled.div`
  padding: 22px 54px;
  display: flex;
  gap: 11px;

  @media screen and (max-width: 1500px) {
    flex-direction: column;
    align-items: center;
  }
`;

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
  font-size: 20px;
  line-height: 150%;
  white-space: pre-wrap;
`;
