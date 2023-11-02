import styled from '@emotion/styled';
import { SearchSuggestion } from '~/types/api.types';
import { ResultItem } from './ResultItem';

type Props = {
  suggestions: SearchSuggestion[];
};

export const ResultBox: React.FC<Props> = ({ suggestions }) => {
  return (
    <StyledResultBox>
      <StyledResultList>
        {
          suggestions.map((suggestion) => (
            <ResultItem key={suggestion.id} suggestion={suggestion} />
          ))
        }
      </StyledResultList>
    </StyledResultBox>
  );
};

const StyledResultBox = styled.div`
  box-sizing: border-box;
  width: 100%;
  position: absolute;
  left: 0;
  top: 0;
  margin-top: 44px;
  padding: 12px 0;
  border-top: 1px solid #e0e0e0;
  border-radius: 0 0 4px 4px;
  background-color: #ffffff;
  box-shadow: 0 2px 4px 0 rgb(0 0 0 / 10%);
`;

const StyledResultList = styled.ul``;
