export const getQueryString = (
  params:
    | string
    | string[][]
    | Record<string, string | number>
    | URLSearchParams,
) => {
  let searchParams: URLSearchParams;

  if (typeof params === 'string' || Array.isArray(params)) {
    searchParams = new URLSearchParams(params);
  } else if (params instanceof URLSearchParams) {
    searchParams = params;
  } else {
    searchParams = new URLSearchParams();

    Object.entries(params).forEach(([key, value]) => {
      const paramValue = typeof value === 'number' ? String(value) : value;
      searchParams.append(key, paramValue);
    });
  }

  const result = searchParams.toString();
  const hasQuery = result.length > 0;

  return hasQuery ? `?${searchParams.toString()}` : '';
};
