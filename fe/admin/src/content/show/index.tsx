import { Container, Typography } from '@mui/material';
import { Helmet } from 'react-helmet-async';
import PageTitleWrapper from 'src/components/PageTitleWrapper';

function Show() {
  return (
    <>
      <Helmet>
        <title>공연 관리</title>
      </Helmet>
      <PageTitleWrapper>
        <Typography variant="h3" component="h3" gutterBottom>
          공연 관리 페이지
        </Typography>
      </PageTitleWrapper>
      <Container maxWidth="lg">공연 관리 페이지</Container>
    </>
  );
}

export default Show;
