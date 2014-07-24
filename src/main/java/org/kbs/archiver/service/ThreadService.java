package org.kbs.archiver.service;
/**
 * Created by kcn on 14-7-24.
 */

import org.kbs.archiver.dao.ThreadDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class ThreadService {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadService.class);

    @Autowired
    private ThreadDAO threadDAO;

    public List<Long> getThreadsByBoard(long boardid) {

    }
}
