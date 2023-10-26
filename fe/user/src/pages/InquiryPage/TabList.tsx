import styled from '@emotion/styled';

export type InquiryTab = {
  id: number;
  name: '공연정보' | '등록문의' | '기타문의';
};

type Props = {
  activaTabId: number;
  onChange: (InquiryType: InquiryTab) => void;
};

export const TypeFilter: React.FC<Props> = ({ activaTabId, onChange }) => {
  const tabList: InquiryTab[] = [
    {
      id: 1,
      name: '공연정보',
    },
    {
      id: 2,
      name: '등록문의',
    },
    {
      id: 3,
      name: '기타문의',
    },
  ];

  return (
    <StyledList>
      {tabList.map((tab) => {
        return (
          <li key={tab.id} className={activaTabId === tab.id ? 'active' : ''}>
            <button onClick={() => onChange(tab)}>{tab.name}</button>
          </li>
        );
      })}
    </StyledList>
  );
};

const StyledList = styled.ul`
  width: 100%;
  display: flex;
  align-items: flex-start;
  gap: 30px;
  border-bottom: 1px solid #c7c7c7;

  & li {
    padding: 0 5px 17px;

    &:hover,
    &.active {
      border-bottom: 4px solid #484848;

      & button {
        color: #1b1b1b;
      }
    }

    &:hover,
    & *:hover {
      cursor: pointer;
    }

    & button {
      font-size: 22px;
      font-weight: bold;
      color: #959595;
    }
  }
`;
