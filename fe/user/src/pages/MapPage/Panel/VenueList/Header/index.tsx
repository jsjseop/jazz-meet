import { DatePicker } from '@components/DatePicker';
import { PillShapeButton } from '@components/PillShapeButton';
import styled from '@emotion/styled';

export const Header: React.FC = () => {
  return (
    <StyledHeader>
      <MainSection>
        <TotalCount>
          <h2>전체</h2>
          <span>4</span>
        </TotalCount>

        <SortSelect>
          <option value="">정렬</option>
          <option value="">거리순</option>
          <option value="">최신순</option>
        </SortSelect>
      </MainSection>
      <SubSection>
        <PillShapeButton>10월13일</PillShapeButton>
        <PillShapeButton>10월14일</PillShapeButton>
        <PillShapeButton>10월15일 19:30</PillShapeButton>

        <DatePicker />
      </SubSection>
    </StyledHeader>
  );
};

const StyledHeader = styled.div``;

const MainSection = styled.section`
  display: flex;
  justify-content: space-between;
`;

const SubSection = styled.section`
  margin-top: 20px;
  display: flex;
  gap: 8px;
`;

const TotalCount = styled.div`
  display: flex;
  gap: 13px;
  font-size: 26px;
  font-weight: 800;

  & h2 {
    color: #212121;
  }

  & span {
    color: #ff4d00;
  }
`;

const SortSelect = styled.select`
  padding: 4px 0;
  border: none;
  font-size: 22px;
  font-weight: 600;
  color: #575757;
  text-align: center;

  &:hover {
    cursor: pointer;
    outline: 1px solid #575757;
  }

  &:focus,
  &:active {
    outline: none;
  }

  & > option:checked {
    background-color: #DCDCDC;
    color: #888888;
  }
`;
