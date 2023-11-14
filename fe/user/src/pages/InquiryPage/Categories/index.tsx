import styled from '@emotion/styled';

import { InquiryCategories } from '~/types/inquiry.types';
import { Category } from './Category';

type Props = {
  currentCategory: InquiryCategories;
  setCategory: (category: InquiryCategories) => void;
};

export const Categories: React.FC<Props> = ({
  currentCategory,
  setCategory,
}) => {
  return (
    <StyledCategories>
      {categories.map((category, index) => (
        <Category
          key={index}
          isSelected={currentCategory === category}
          category={category}
          onClick={() => setCategory(category)}
        />
      ))}
    </StyledCategories>
  );
};

const categories: InquiryCategories[] = ['서비스', '등록', '기타'];

const StyledCategories = styled.ul`
  width: 100%;
  display: flex;
  align-items: flex-start;
  gap: 30px;
  border-bottom: 1px solid #c7c7c7;
`;
