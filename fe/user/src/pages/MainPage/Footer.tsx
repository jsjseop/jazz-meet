import styled from '@emotion/styled';
import GitHubIcon from '@mui/icons-material/GitHub';
import InstagramIcon from '@mui/icons-material/Instagram';
import TelegramIcon from '@mui/icons-material/Telegram';

export const Footer: React.FC = () => {
  return (
    <StyledFooter>
      <StyledFooterContent>
        <img
          src="https://github.com/jazz-meet/jazz-meet/assets/57666791/55a430ea-4249-4e9a-966f-2fc71c0bd7d7"
          alt="logo"
        />
        <StyledIcons>
          <GitHubIcon
            onClick={() =>
              (location.href = 'https://github.com/jazz-meet/jazz-meet')
            }
          />
          <InstagramIcon />
          <TelegramIcon />
        </StyledIcons>
        <StyledButtons>
          <div>ABOUT US</div>
          <div>CONTACT</div>
        </StyledButtons>
        <StyledCopyRightText>
          <div>
            재즈밋의 모든 게시물은 크리에이티브 커먼즈 2.0 "저작자
            표시-비영리-변경금지 (BY-NC-ND)" 라이선스에 따라에 따라 이용하실 수
            있습니다.
          </div>
          <div>© 2023 Jazz Meet. All rights reserved.</div>
        </StyledCopyRightText>
      </StyledFooterContent>
    </StyledFooter>
  );
};

const StyledFooter = styled.div`
  background-color: #000;
  color: #fff;
  padding: 90px 0;
`;

const StyledFooterContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 26px;

  img {
    width: 150px;
  }
`;

const StyledIcons = styled.div`
  display: flex;
  gap: 18px;

  svg {
    width: 20px;
    cursor: pointer;
  }
`;

const StyledButtons = styled.div`
  color: #686970;
  letter-spacing: -4%;
  font-size: 12px;
  font-weight: 600;
  display: flex;
  gap: 10px;
`;

const StyledCopyRightText = styled.div`
  color: #686970;
  letter-spacing: -4%;
  font-size: 10px;
  font-weight: 600;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 18px;
`;
