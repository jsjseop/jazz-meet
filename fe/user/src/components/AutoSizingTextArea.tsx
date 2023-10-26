import styled from '@emotion/styled';
import React, { useEffect, useMemo, useRef } from 'react';

type Props = {
  onChange: (value: string) => void;
  minRows?: number;
} & Omit<React.TextareaHTMLAttributes<HTMLTextAreaElement>, 'onChange'>;

export const AutoSizingTextArea: React.FC<Props> = ({
  onChange,
  minRows = 1,
  ...rest
}) => {
  const minHeight = useMemo(() => minRows * 20, [minRows]);
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  useEffect(() => {
    const textarea = textareaRef.current;
    if (!textarea) return;

    textarea.style.height = 'auto';
    textarea.style.height = `${Math.max(textarea.scrollHeight, minHeight)}px`;
  }, [rest.value, minHeight]);

  const onChangeTextarea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { value } = e.target;

    onChange(value);
  };

  return (
    <StyledTextArea {...rest} ref={textareaRef} onChange={onChangeTextarea} />
  );
};

const StyledTextArea = styled.textarea`
  border-radius: 2px;
`;
