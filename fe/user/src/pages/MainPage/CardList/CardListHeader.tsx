import styled from '@emotion/styled';

type Props = {
  title: string;
  onMoreClick?: () => void;
  tabItems?: {
    name: string;
    onClick: () => void;
  }[];
};

export const CardListHeader: React.FC<Props> = ({
  title,
  onMoreClick,
  tabItems,
}) => {
  return (
    <StyledCardListHeader>
      <LeftContainer>
        <Title>{title}</Title>
        {tabItems && (
          <Tabs>
            {tabItems.map((item, index) => {
              return (
                <Tab key={index} onClick={item.onClick}>
                  {item.name}
                </Tab>
              );
            })}
          </Tabs>
        )}
      </LeftContainer>

      {onMoreClick && <MoreButton>더보기</MoreButton>}
    </StyledCardListHeader>
  );
};

const StyledCardListHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

const LeftContainer = styled.div`
  display: flex;
  align-items: center;
`;

const Title = styled.div`
  font-size: 24px;
  color: #ff4d00;
`;

const Tabs = styled.div`
  display: flex;
  gap: 32px;
  justify-content: space-between;
  align-items: center;
`;

const Tab = styled.div`
  cursor: pointer;
`;

const MoreButton = styled.button`
  cursor: pointer;
`;
