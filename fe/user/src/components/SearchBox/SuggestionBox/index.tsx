import styled from '@emotion/styled';
import { useEffect } from 'react';
import { SearchSuggestion } from '~/types/api.types';
import { SuggestionItem } from './SuggestionItem';

type Props = {
  suggestions: SearchSuggestion[];
  open: boolean;
  searchBoxRef: React.RefObject<HTMLDivElement>;
  activeIndex: number;
  onClose: () => void;
};

export const SuggestionBox: React.FC<Props> = ({
  suggestions,
  open,
  searchBoxRef,
  activeIndex,
  onClose
}) => {
  useEffect(() => {
    const onSuggestionItemClick = (e: MouseEvent) => {
      const target = e.target as HTMLElement;

      if (searchBoxRef.current?.contains(target)) {
        return;
      }

      onClose();
    };

    document.addEventListener('click', onSuggestionItemClick);

    return () => {
      document.removeEventListener('click', onSuggestionItemClick);
    };
  }, [onClose, searchBoxRef]);

  return (
    <StyledSuggestionBox $open={open}>
      <StyledSuggestionList>
        {suggestions.map((suggestion, index) => (
          <SuggestionItem key={suggestion.id} suggestion={suggestion} onClose={onClose} active={index === activeIndex} />
        ))}
      </StyledSuggestionList>
    </StyledSuggestionBox>
  );
};

const StyledSuggestionBox = styled.div<{ $open: boolean }>`
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
  display: ${({ $open }) => ($open ? 'block' : 'none')};
`;

const StyledSuggestionList = styled.ul``;
