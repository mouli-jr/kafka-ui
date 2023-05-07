import React from 'react';
import useAppParams from 'lib/hooks/useAppParams';
import { ClusterName } from 'redux/interfaces';
import { useSearchParams } from 'react-router-dom';
import { useTopicNames } from 'lib/hooks/api/topics';
import { PER_PAGE } from 'lib/constants';

const SearchResult: React.FC = () => {
  const { clusterName } = useAppParams<{ clusterName: ClusterName }>();
  const [searchParams] = useSearchParams();
  const { data } = useTopicNames({
    clusterName,
    page: Number(1),
    perPage: Number(searchParams.get('perPage') || 7),
    search: searchParams.get('q') || "*",
    showInternal: !searchParams.has('hideInternal'),
    orderBy: undefined,
    sortOrder: undefined,
  });

  const topics = data?.topics || [];

  console.log(topics);

  //Return the topic names as list suggestions for the search bar
  return (
    <div>
      {topics?.map((topic) => (
        <div key={topic}>
          { <a href={`/ui/clusters/${clusterName}/all-topics/${topic}`}>
            {topic}
          </a> }
        </div>
      ))}
    </div>
  );
};

export default SearchResult;
