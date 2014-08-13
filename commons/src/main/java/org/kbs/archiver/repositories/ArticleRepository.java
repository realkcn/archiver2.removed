package org.kbs.archiver.repositories;

import org.kbs.archiver.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kcn on 14-8-10.
 */

@Repository
public interface ArticleRepository extends CrudRepository<Article, String> {
}
