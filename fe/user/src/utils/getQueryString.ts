export const getQueryString = (params: Record<string, string | number>) => {
  const searchParams = new URLSearchParams();

  Object.entries(params).forEach(([key, value]) => {
    if (key && value) {
      searchParams.append(key, String(value));
    }
  });

  const result = searchParams.toString();
  const hasQuery = result.length > 0;

  return hasQuery ? `?${searchParams.toString()}` : '';
};
