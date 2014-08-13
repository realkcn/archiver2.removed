package org.kbs.archiver.repositories;

import org.kbs.archiver.model.OriginArticleInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kcn on 14-8-10.
 */

@Repository
public interface OriginArticleInfoRepository extends CrudRepository<OriginArticleInfo, String> {
}
