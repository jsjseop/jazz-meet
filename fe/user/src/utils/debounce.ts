type CallbackFunction = (...args: unknown[]) => void;

export const debounce = <T extends CallbackFunction>(callback: T, delay: number): (...args: Parameters<T>) => void  => {
  let timer: number;

  return (...args: Parameters<T>) => {
    clearTimeout(timer);
    timer = setTimeout(() => {
      callback(...args);
    }, delay);
  };
}
