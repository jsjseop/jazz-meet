import { BASE_URL } from '~/constants/ENV_VARIABLES';

export const fetchData = async (path: string, options?: RequestInit) => {
  const response = await fetch(BASE_URL + path, options);

  return response;
};
