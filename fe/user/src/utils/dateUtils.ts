const weekDays = ['일', '월', '화', '수', '목', '금', '토'];

export const getKoreanWeekdayName = (day: number) => {
  return weekDays[day];
};

export const getFormattedYearMonth = (date: Date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const formattedDate = `${year}${month}`;

  return formattedDate;
}; // ex) 202301

export const getFormattedDate = (date: Date) => {
  const datePart = getFormattedYearMonth(date);
  const day = String(date.getDate()).padStart(2, '0');
  const formattedDate = `${datePart}${day}`;

  return formattedDate;
}; // ex) 20230101

export const getFormattedDateTime = (date: Date) => {
  const datePart = getFormattedDate(date);
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const formattedDateTime = `${datePart}${hours}${minutes}`;

  return formattedDateTime;
}; // ex) 202301011230

export const getFormattedDateString = (date: string | Date) => {
  const dateObj = new Date(date);

  return `${dateObj.getFullYear()}.${(dateObj.getMonth() + 1)
    .toString()
    .padStart(2, '0')}.${dateObj.getDate().toString().padStart(2, '0')}`;
}; // ex) 2023.01.01
