export type InquiryCategories = '서비스' | '등록' | '기타';

export type Inquiry = {
  id: number;
  status: '검토중' | '답변완료';
  content: string;
  nickname: string;
  createdAt: string;
};
