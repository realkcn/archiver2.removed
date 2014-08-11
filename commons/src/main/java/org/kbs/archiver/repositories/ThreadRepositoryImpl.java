package org.kbs.archiver.repositories;/**
 * Created by kcn on 14-8-10.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ThreadRepositoryImpl implements ThreadRepositoryCustom {
    private static final Logger LOG = LoggerFactory
            .getLogger(ThreadRepositoryImpl.class);
//    @Autowired
//    ThreadRepository threadRepository;

    //    @Override
    public List<org.kbs.archiver.model.Thread> MyCustomBatchOperation() {
        return null;
    }
}
