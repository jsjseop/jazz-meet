import styled from '@emotion/styled';

export const Panel: React.FC = () => {
  return (<StyledPanel>
    
    <VenueList>
      <Header />
      <Venues>
        <VenueItem/>
        <VenueItem/>
        <VenueItem/>
      </Venues>
      <Pagenation/>
    <VenueList />
      
    <VenueDetail>
      <ImageGrid />
      <Header />
      <BasicInfo />
      <RestInfo />
      { showInfoDetail && <InfoDetail /> }
      { showInfoDetail && createPortal(<Images/> )}
    </VenueDetail>
  </StyledPanel>)
};

const StyledPanel = styled.div`
  width: 100%;
  height: calc(100% - 73px);
`;
