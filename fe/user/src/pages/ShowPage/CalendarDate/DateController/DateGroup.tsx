import styled from '@emotion/styled';

export const DateGroup: React.FC = () => {
  const dates = [
    {
      day: '수',
      date: 1,
    },
    {
      day: '목',
      date: 2,
    },
    {
      day: '금',
      date: 3,
    },
    {
      day: '토',
      date: 4,
    },
    {
      day: '일',
      date: 5,
    },
    {
      day: '월',
      date: 6,
    },
    {
      day: '화',
      date: 7,
    },
    {
      day: '수',
      date: 8,
    },
    {
      day: '목',
      date: 9,
    },
    {
      day: '금',
      date: 10,
    },
    {
      day: '토',
      date: 11,
    },
    {
      day: '일',
      date: 12,
    },
    {
      day: '월',
      date: 13,
    },
  ];

  return (
    <StyledDateGroup>
      {dates.map(({ day, date }) => (
        <StyledDate>
          <p>{day}</p>
          <p>{date}</p>
        </StyledDate>
      ))}
    </StyledDateGroup>
  );
};

const StyledDateGroup = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;
`;

const StyledDate = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 24px;

  > p {
    padding: 6px 0;
  }

  > p:first-of-type {
    font-size: 18px;
    font-weight: 500;
    line-height: 1.4;
    color: #a3a4a9;
  }

  > p:last-of-type {
    font-size: 28px;
    font-weight: 700;
    color: #686970;
  }
`;
