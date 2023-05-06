import React from 'react';
import { render, WithRoute } from 'lib/testHelpers';
import { screen } from '@testing-library/react';
import ClusterContext from 'components/contexts/ClusterContext';
import userEvent from '@testing-library/user-event';
import { clusterTopicsPath } from 'lib/paths';
import ListPage from 'components/Topics/List/ListPage';

const clusterName = 'test-cluster';

jest.mock('components/Topics/List/TopicTable', () => () => <>TopicTableMock</>);
jest.mock('components/Topics/List/SearchResult', () => () => <div>SearchResultMock</div>);

describe('ListPage Component', () => {
  const renderComponent = () =>
    render(
      <ClusterContext.Provider
        value={{
          isReadOnly: false,
          hasKafkaConnectConfigured: true,
          hasSchemaRegistryConfigured: true,
          isTopicDeletionAllowed: true,
        }}
      >
        <WithRoute path={clusterTopicsPath()}>
          <ListPage />
        </WithRoute>
      </ClusterContext.Provider>,
      { initialEntries: [clusterTopicsPath(clusterName)] }
    );

  describe('Component Render', () => {
    beforeEach(() => {
      renderComponent();
    });
    it('handles switch of Internal Topics visibility', async () => {
      const switchInput = screen.getByLabelText('Show Internal Topics');
      expect(switchInput).toBeInTheDocument();

      expect(global.localStorage.getItem('hideInternalTopics')).toBeNull();
      await userEvent.click(switchInput);
      expect(global.localStorage.getItem('hideInternalTopics')).toBeTruthy();
      await userEvent.click(switchInput);
      expect(global.localStorage.getItem('hideInternalTopics')).toBeNull();
    });

    it('renders the TopicsTable', () => {
      expect(screen.getByText('TopicTableMock')).toBeInTheDocument();
    });

    it('empty Search Results', () => {
      expect(screen.getByPlaceholderText('Search by Topic Name')).toBeInTheDocument();

      const searchInput = screen.getByPlaceholderText('Search by Topic Name');
      expect(screen.getByText('SearchResultMock')).not.toBeInTheDocument;
    });

    it('renders the Search Results', () => {
      expect(screen.getByPlaceholderText('Search by Topic Name')).toBeInTheDocument();

      const searchInput = screen.getByPlaceholderText('Search by Topic Name');
      userEvent.type(searchInput, '_test_');
      expect(screen.getByText('SearchResultMock')).toBeInTheDocument();
    });
  });
});
