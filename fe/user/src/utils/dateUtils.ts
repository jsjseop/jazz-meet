const weekDays = ['일', '월', '화', '수', '목', '금', '토'];

export const getKoreanWeekdayName = (day: number) => {
  return weekDays[day];
};

export const getFormattedYearMonth = (date: Date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const formattedDate = `${year}${month}`;

  return formattedDate;
};

export const getFormattedDate = (date: Date) => {
  const datePart = getFormattedYearMonth(date);
  const day = String(date.getDate()).padStart(2, '0');
  const formattedDate = `${datePart}${day}`;

  return formattedDate;
};

export const getFormattedDateTime = (date: Date) => {
  const datePart = getFormattedDate(date);
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const formattedDateTime = `${datePart}${hours}${minutes}`;

  return formattedDateTime;
};

export const getFirstDay = (year: number, month: number) => {
  return new Date(year, month - 1, 1).getDay();
};

export const getLastDate = (year: number, month: number) => {
  return new Date(year, month, 0).getDate(); // 0일은 지난 달의 마지막 날을 의미합니다.
};

export const getMonthDates = (firstDay: number, lastDate: number) => {
  const dates = [];

  for (let i = 1; i <= lastDate; i++) {
    dates.push({
      date: i,
      day: getKoreanWeekdayName((firstDay + i - 1) % 7),
    });
  }

  return dates;
};
