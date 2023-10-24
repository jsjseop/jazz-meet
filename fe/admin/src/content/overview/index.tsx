import { Box, Button, Card, Container } from '@mui/material';
import TextField from '@mui/material/TextField';

import { Link as RouterLink } from 'react-router-dom';

import { Helmet } from 'react-helmet-async';

import { styled } from '@mui/material/styles';

const OverviewWrapper = styled(Box)(
  () => `
    overflow: auto;
    flex: 1;
    overflow-x: hidden;
    align-items: center;
`
);

function Overview() {
  return (
    <OverviewWrapper>
      <Helmet>
        <title>admin 인증</title>
      </Helmet>
      <Container maxWidth="lg">
        <Card
          sx={{
            p: 5,
            width: '30%',
            margin: 'auto',
            my: 15,
            borderRadius: 2,
            display: 'flex',
            justifyContent: 'center',
            alignItems: 'center',
            flexDirection: 'column',
            gap: 2
          }}
        >
          <TextField
            id="outlined-id-input"
            label="ID"
            type="text"
            autoComplete="current-id"
            sx={{ width: '100%', alignSelf: 'flex-start' }}
          />
          <TextField
            id="outlined-password-input"
            label="Password"
            type="password"
            autoComplete="current-password"
            sx={{ width: '100%', alignSelf: 'flex-start' }}
          />
          <Box sx={{ width: '100%', display: 'flex', gap: 1}}>
            <Button
              sx={{ flex: 1}}
              component={RouterLink}
              to="/venues"
              size="large"
              variant="contained"
            >
              입장
            </Button>
          </Box>
        </Card>
      </Container>
    </OverviewWrapper>
  );
}

export default Overview;
