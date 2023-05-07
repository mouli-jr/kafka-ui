import React from 'react';
import { render, WithRoute } from 'lib/testHelpers';
import { screen, within } from '@testing-library/react';
import { TopicNamesResponse } from 'generated-sources';
import { topicNamesPayload } from 'lib/fixtures/topics';
import ClusterContext from 'components/contexts/ClusterContext';
import {
  useTopicNames,
} from 'lib/hooks/api/topics';
import { clusterTopicsPath } from 'lib/paths';
import SearchResult from 'components/Topics/List/SearchResult';

const clusterName = 'test-cluster';

jest.mock('lib/hooks/redux', () => ({
  ...jest.requireActual('lib/hooks/redux'),
  useAppDispatch: jest.fn(),
}));

const getButtonByName = (name: string) => screen.getByRole('button', { name });

jest.mock('lib/hooks/api/topics', () => ({
  ...jest.requireActual('lib/hooks/api/topics'),
  useTopicNames: jest.fn(),
}));

describe('SearchResult Components', () => {

  const renderComponent = (
    currentData: TopicNamesResponse | undefined = undefined,
    isReadOnly = false,
    isTopicDeletionAllowed = true
  ) => {
    (useTopicNames as jest.Mock).mockImplementation(() => ({
      data: currentData,
    }));

    return render(
      <ClusterContext.Provider
        value={{
          isReadOnly,
          hasKafkaConnectConfigured: true,
          hasSchemaRegistryConfigured: true,
          isTopicDeletionAllowed,
        }}
      >
        <WithRoute path={clusterTopicsPath()}>
          <SearchResult />
        </WithRoute>
      </ClusterContext.Provider>,
      {
        initialEntries: [clusterTopicsPath(clusterName)],
      }
    );
  };


  describe('with topics', () => {
    it('renders none of search results for undefined', () => {
      renderComponent({ topics: undefined, pageCount: 1 });
      expect(
        screen.queryByText('__internal.topic' )
      ).toBeNull();
    });
    it('renders none of search results', () => {
      renderComponent({ topics: [], pageCount: 1 });
      expect(
        screen.queryByText('__internal.topic' )
      ).toBeNull();
    });
    it('renders part of search results', () => {
      renderComponent({ topics: topicNamesPayload, pageCount: 1 });
      expect(
        screen.getByRole('link', { name: '__internal.topic' })
      ).toBeInTheDocument();
    });
    it('renders all search results', () => {
      renderComponent({ topics: topicNamesPayload, pageCount: 1 });
      expect(
        screen.getByRole('link', { name: '__internal.topic' })
      ).toBeInTheDocument();
      expect(
        screen.getByRole('link', { name: 'external.topic' })
      ).toBeInTheDocument();
    });
  });
});
