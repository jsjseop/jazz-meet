import {
  GetInquiryParams,
  InquiryData,
  InquiryDetail,
  PostInquiryParams,
} from '~/types/api.types';
import { getQueryString } from '~/utils/getQueryString';
import { fetchData } from './fetchData';

export const getInquiryData = async (
  params: GetInquiryParams,
): Promise<InquiryData> => {
  const queryString = getQueryString(params);
  const response = await fetchData(`/api/inquiries${queryString}`);

  return response.json();
};

export const getInquiryDetail = async (id: number): Promise<InquiryDetail> => {
  const response = await fetchData(`/api/inquiries/${id}`);

  return response.json();
};

export const postInquiryData = async (params: PostInquiryParams) => {
  const response = await fetchData('/api/inquiries', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(params),
  });

  const data = await response.json();

  if (!response.ok) {
    throw new Error(data.errorMessage);
  }

  return data;
};

export const deleteInquiry = async (inquiryId: number, password: string) => {
  const response = await fetchData(`/api/inquiries/${inquiryId}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ password }),
  });

  if (response.status === 400) {
    const data = await response.json();

    throw data;
  }
};
