import styled from '@emotion/styled';
import ArrowBackIosNewIcon from '@mui/icons-material/ArrowBackIosNew';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import usePagination from '@mui/material/usePagination/usePagination';
import React from 'react';

type Props = {
  maxPage: number;
  currentPage: number;
  onChange: (event: React.ChangeEvent<unknown>, value: number) => void;
};

export const PaginationBox: React.FC<Props> = ({
  maxPage,
  currentPage,
  onChange,
}) => {
  const { items } = usePagination({
    count: maxPage,
    page: currentPage,
    onChange,
  });

  return (
    <StyledNavigation>
      <StyledButtonList>
        {items.map(({ page, type, selected, ...item }, index) => {
          if (type === 'start-ellipsis' || type === 'end-ellipsis') {
            return <li key={index}>...</li>;
          }

          if (type === 'page') {
            return (
              <li key={index}>
                <StyledNumberButton type="button" selected={selected} {...item}>
                  {page}
                </StyledNumberButton>
              </li>
            );
          }

          return (
            <li key={index}>
              <StyledArrowButton type="button" {...item}>
                {type === 'previous' ? (
                  <ArrowBackIosNewIcon />
                ) : (
                  <ArrowForwardIosIcon />
                )}
              </StyledArrowButton>
            </li>
          );
        })}
      </StyledButtonList>
    </StyledNavigation>
  );
};

const StyledNavigation = styled.nav`
  margin: 36px auto;
  display: flex;
  justify-content: center;
`;

const StyledButtonList = styled.ul`
  display: flex;
  align-items: center;
  gap: 12px;

  & * {
    font-size: 22px;
  }

  & button:hover {
    cursor: pointer;
    opacity: 0.7;
  }
`;

const StyledNumberButton = styled.button<{ selected: boolean }>`
  min-width: 32px;
  height: 32px;
  padding: 0 4px;
  font-weight: ${({ selected }) => (selected ? 'bold' : 'normal')};
  color: ${({ selected }) => (selected ? '#FF4D00' : '#959595')};
`;

const StyledArrowButton = styled.button`
  min-width: 32px;
  height: 32px;
  display: flex;
  justify-content: center;
  align-items: center;
`;
