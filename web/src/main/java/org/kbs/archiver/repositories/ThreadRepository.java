package org.kbs.archiver.repositories;

import org.kbs.archiver.model.*;
import org.kbs.archiver.model.Thread;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by kcn on 14-8-10.
 */
public interface ThreadRepository extends CrudRepository<Thread, String> {
}
