package org.kbs.archiver.repositories;

import org.kbs.archiver.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kcn on 14-8-10.
 */
@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, String>, ThreadRepositoryCustom {
    Page<Thread> findByBoardid(String boardid, Pageable pageable);
}
