package org.kbs.archiver.repositories;

import org.kbs.archiver.model.DeletedArticle;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kcn on 14-8-10.
 */

@Repository
public interface DeletedArticleRepository extends PagingAndSortingRepository<DeletedArticle, String> {
}
