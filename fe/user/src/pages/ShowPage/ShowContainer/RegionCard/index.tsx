import styled from '@emotion/styled';
import { VenueCard } from './VenueCard';

export const RegionCard: React.FC = () => {
  const venues = [
    {
      id: 1,
      name: '클럽 에반스',
      shows: [
        {
          id: 1,
          posterUrl: 'poster.url',
          teamName: '카즈미 타테이시 트리오',
          startTime: '2023-10-19T18:30:00', // KST
          endTime: '2023-10-19T21:00:00',
        },
        {
          id: 2,
          posterUrl: 'poster.url',
          teamName: 'WE필하모닉스의 피아노 퀸텟',
          startTime: '2023-10-19T21:30:00',
          endTime: '2023-10-19T23:00:00',
        },
      ],
    },
    {
      id: 2,
      name: '부기우기',
      shows: [
        {
          id: 3,
          posterUrl: 'poster.url',
          teamName: '최정수 타이니 오케스터',
          startTime: '2023-10-19T18:30:00', // KST
          endTime: '2023-10-19T19:30:00',
        },
        {
          id: 4,
          posterUrl: 'poster.url',
          teamName: '마인드 컬렉티브',
          startTime: '2023-10-19T20:00:00',
          endTime: '2023-10-19T21:00:00',
        },
        {
          id: 5,
          posterUrl: 'poster.url',
          teamName: '마드모아젤S',
          startTime: '2023-10-19T21:30:00',
          endTime: '2023-10-19T22:30:00',
        },
      ],
    },
    {
      id: 3,
      name: '올 댓 재즈',
      shows: [
        {
          id: 6,
          posterUrl: 'poster.url',
          teamName: '서울재즈쿼텟',
          startTime: '2023-10-19T18:00:00', // KST
          endTime: '2023-10-19T19:00:00',
        },
        {
          id: 7,
          posterUrl: 'poster.url',
          teamName: '김영후 빅밴드 단독공연',
          startTime: '2023-10-19T19:30:00',
          endTime: '2023-10-19T20:30:00',
        },
        {
          id: 8,
          posterUrl: 'poster.url',
          teamName: '콰르텟 앤 〈The Red Tango〉',
          startTime: '2023-10-19T21:00:00',
          endTime: '2023-10-19T22:00:00',
        },
        {
          id: 9,
          posterUrl: 'poster.url',
          teamName: '석지민트리오 첫 번째 단독 공연 ‘Portraits’…',
          startTime: '2023-10-19T22:30:00',
          endTime: '2023-10-19T23:30:00',
        },
      ],
    },
  ];

  return (
    <StyledRegionCard>
      <StyledCardHeader>서울시 강남구</StyledCardHeader>
      <StyledVenueCardList>
        {venues.map((venue) => (
          <VenueCard key={venue.id} name={venue.name} shows={venue.shows} />
        ))}
      </StyledVenueCardList>
    </StyledRegionCard>
  );
};

const StyledRegionCard = styled.div``;

const StyledCardHeader = styled.h2`
  height: 52px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 4px 4px 0 0;
  background-color: #1b1b1b;
  color: #ffffff;
  font-size: 24px;
  font-weight: bold;
`;

const StyledVenueCardList = styled.div`
  margin-top: 22px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 22px;
`;
