'use client';

import { useCallback, useEffect, useState } from 'react';
import { Pageable, PageResponse } from '../types/types';

export type PaginatedProps<T> = {
  fetch: (pageable: Pageable) => Promise<PageResponse<T>>;
  size?: number;
};

export type PaginatedData<T> = {
  page: number;
  data: PageResponse<T>;
};

const usePaginated = <T>({ fetch, size = 200 }: PaginatedProps<T>) => {
  const [pageData, setPageData] = useState<PaginatedData<T>>();
  const [hasMore, setHasMore] = useState(true);

  const fetchPaged = useCallback(
    async (page: number, refresh: boolean = false) => {
      const { items, ...res } = await fetch({ page, size });
      setPageData((prev) => {
        const newItems = [];
        if (prev?.data.items && !refresh) {
          newItems.push(...prev.data.items);
        }
        newItems.push(...items);

        console.log(items);
        return {
          page,
          data: {
            ...res,
            items: newItems,
          },
        };
      });
      if (page === res.totalPages - 1) {
        setHasMore(false);
      }
    },
    [fetch, size]
  );

  const getNextPage = useCallback(async () => {
    if (pageData && pageData.page <= pageData.data.totalPages - 1) {
      await fetchPaged(pageData.page + 1);
    }
  }, [fetchPaged, pageData]);

  useEffect(() => {
    if (!pageData?.data) {
      (async () => {
        await fetchPaged(0, true);
      })();
    }
  }, [fetchPaged, pageData?.data]);

  const refresh = useCallback(async () => {
    const maxPage = pageData?.page ?? 0;
    await fetchPaged(0, true);
    for (let i = 1; i < maxPage; i++) {
      await getNextPage();
    }
  }, [fetchPaged, getNextPage, pageData?.page]);

  return [pageData?.data.items ?? [], getNextPage, hasMore, refresh] as const;
};

export default usePaginated;
