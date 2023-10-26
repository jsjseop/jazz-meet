import { Cards } from './CardList/Cards';
import { CardList } from './CardList';
import { CardListHeader } from './CardList/CardListHeader';

export const AroundVenus: React.FC = () => {
  const posters = [
    {
      id: 1,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150831_275%2F1441004893369gYh5w_JPEG%2F11611824_0.jpg',
      name: 'Evans',
      address: '서울 마포구 와우산로 63 2층',
    },
    {
      id: 2,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20230408_221%2F1680929454542Aovwz_JPEG%2FKakaoTalk_20230214_192728451.jpg',
      name: '부기우기',
      address: '서울 용산구 회나무로 21 2층',
    },
    {
      id: 3,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210402_140%2F1617367262454YU3Hd_JPEG%2FNsOFazqvYXyXDXceOEjH3cm6.jpg',
      name: '블루밍 재즈바',
      address: '서울 강남구 테헤란로19길 21 지하1층',
    },
    {
      id: 4,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20201118_121%2F1605662603727ABSSF_JPEG%2FKakaoTalk_20200919_012029316.jpg',
      name: '플랫나인',
      address: '서울 서초구 강남대로65길 10 5층',
    },
    {
      id: 5,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20150831_275%2F1441004893369gYh5w_JPEG%2F11611824_0.jpg',
      name: 'Evans',
      address: '서울 마포구 와우산로 63 2층',
    },
    {
      id: 6,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20230408_221%2F1680929454542Aovwz_JPEG%2FKakaoTalk_20230214_192728451.jpg',
      name: '부기우기',
      address: '서울 용산구 회나무로 21 2층',
    },
    {
      id: 7,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210402_140%2F1617367262454YU3Hd_JPEG%2FNsOFazqvYXyXDXceOEjH3cm6.jpg',
      name: '블루밍 재즈바',
      address: '서울 강남구 테헤란로19길 21 지하1층',
    },
    {
      id: 8,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20201118_121%2F1605662603727ABSSF_JPEG%2FKakaoTalk_20200919_012029316.jpg',
      name: '플랫나인',
      address: '서울 서초구 강남대로65길 10 5층',
    },
    {
      id: 9,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20210402_140%2F1617367262454YU3Hd_JPEG%2FNsOFazqvYXyXDXceOEjH3cm6.jpg',
      name: '블루밍 재즈바',
      address: '서울 강남구 테헤란로19길 21 지하1층',
    },
    {
      id: 10,
      thumbnailUrl:
        'https://search.pstatic.net/common/?src=https%3A%2F%2Fldb-phinf.pstatic.net%2F20201118_121%2F1605662603727ABSSF_JPEG%2FKakaoTalk_20200919_012029316.jpg',
      name: '플랫나인',
      address: '서울 서초구 강남대로65길 10 5층',
    },
  ];

  return (
    <CardList>
      <CardListHeader title="주변 공연장" />

      <Cards posters={posters} />
    </CardList>
  );
};
