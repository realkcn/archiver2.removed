package org.kbs.archiver.dao;/**
 * Created by kcn on 14-8-10.
 */

import org.kbs.archiver.repositories.ThreadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ThreadDAOImpl implements ThreadDAO {
    private static final Logger LOG = LoggerFactory
            .getLogger(ThreadDAOImpl.class);
    @Autowired
    ThreadRepository threadRepository;

    @Override
    public Thread get(String threadid) {
        return null;
    }
}
