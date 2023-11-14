import styled from '@emotion/styled';
import { InquiryCategories } from '~/types/inquiry.types';

type Props = {
  category: InquiryCategories;
  isSelected: boolean;
  onClick: () => void;
};

export const Category: React.FC<Props> = ({
  category,
  isSelected,
  onClick,
}) => {
  return (
    <StyledCategory className={isSelected ? 'active' : ''} onClick={onClick}>
      <button>{category}</button>
    </StyledCategory>
  );
};

const StyledCategory = styled.li`
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
`;
