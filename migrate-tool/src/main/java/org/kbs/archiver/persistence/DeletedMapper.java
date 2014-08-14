package org.kbs.archiver.persistence;

import org.apache.ibatis.annotations.Param;
import org.kbs.archiver.entity.DeletedEntity;

import java.util.List;

/**
 * User: kcn
 * Date: 12-9-26
 * Time: 下午4:15
 */

public interface DeletedMapper {
    public List<DeletedEntity> getArticles(@Param("begin") int begin, @Param("count") int count);

    public void insert(DeletedEntity entity);

    public int count();
}
