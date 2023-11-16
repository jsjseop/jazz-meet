export const validateInputLength = ({
  input,
  minLength,
  maxLength,
  onInvalid,
}: {
  input: string;
  minLength?: number;
  maxLength?: number;
  onInvalid?: (message?: string) => void;
}) => {
  if (!input) {
    if (onInvalid !== undefined) {
      onInvalid('입력해주세요.');
    }
    return false;
  }

  if (minLength !== undefined && input.length < minLength) {
    if (onInvalid !== undefined) {
      onInvalid(`${minLength}자 이상으로 입력해주세요.`);
    }
    return false;
  }

  if (maxLength !== undefined && input.length > maxLength) {
    if (onInvalid !== undefined) {
      onInvalid(`${maxLength}자 이하로 입력해주세요.`);
    }
    return false;
  }

  return true;
};
