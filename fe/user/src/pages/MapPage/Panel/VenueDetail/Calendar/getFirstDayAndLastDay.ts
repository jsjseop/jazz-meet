export const getFirstDay = (year: number, month: number) => {
  // month는 0부터 시작하기 때문에 실제 월을 계산할 때는 +1을 해줍니다.
  const firstDay = new Date(year, month - 1, 1);

  return firstDay.getDay(); // 요일을 숫자로 반환 (0: 일요일, 1: 월요일, ..., 6: 토요일)
};

export const getLastDay = (year: number, month: number) => {
  const lastDay = new Date(year, month, 0); // 0일은 지난 달의 마지막 날을 의미합니다.

  return lastDay.getDate(); // 마지막 날짜를 가져옵니다.
};
