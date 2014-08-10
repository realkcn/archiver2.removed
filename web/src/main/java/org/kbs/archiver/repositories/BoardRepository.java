package org.kbs.archiver.repositories;

import org.kbs.archiver.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kcn on 14-8-10.
 */

@Repository
public interface BoardRepository extends CrudRepository<Board, String> {
    List<Board> findByName(String name);
}
