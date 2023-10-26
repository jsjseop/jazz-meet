import { Tabs } from './Tabs';
import { Tab } from './Tabs/Tab';

export const RestInfo: React.FC = () => {
  return (
    <>
      <Tabs>
        <Tab isSelected>공연정보</Tab>
        <Tab>판매정보</Tab>
        <Tab>공연장정보</Tab>
      </Tabs>
    </>
  );
};
