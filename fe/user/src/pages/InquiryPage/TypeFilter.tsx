import styled from '@emotion/styled';

export type InquiryTypeData = {
  id: number;
  name: '공연정보' | '등록문의' | '기타문의';
};

type Props = {
  activeTypeId: number;
  onChange: (InquiryType: InquiryTypeData) => void;
};

export const TypeFilter: React.FC<Props> = ({ activeTypeId, onChange }) => {
  const typeList: InquiryTypeData[] = [
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
      {typeList.map((type) => {
        return (
          <li
            key={type.id}
            className={activeTypeId === type.id ? 'active' : ''}
          >
            <button onClick={() => onChange(type)}>{type.name}</button>
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
