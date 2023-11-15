export const validateContent = (content: string) => {
  if (!content) {
    alert('문의 내용을 입력해주세요.');
    return false;
  }
  return true;
};

export const validateNickname = (nickname?: string) => {
  if (!nickname) {
    alert('닉네임을 입력해주세요.');
    return false;
  }

  if (nickname.length < 2 || nickname.length > 8) {
    alert('닉네임은 2자 이상 8자 이하로 입력해주세요.');
    return false;
  }
  return true;
};
