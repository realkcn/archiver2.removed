package org.kbs.archiver.repositories;
/**
 * Created by kcn on 14-8-10.
 */

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class ThreadRepositoryImpl implements ThreadRepositoryCustom {
    private static final Logger LOG = LoggerFactory
            .getLogger(ThreadRepositoryImpl.class);
//    @Autowired
//    ThreadRepository threadRepository;

    //    @Override
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void addArticle(ObjectId threadid, ObjectId articleid) {
        mongoTemplate.updateFirst(new Query(Criteria.where("threadid").is(threadid)),
                new Update().push("articles", articleid), Thread.class);
    }

    @Override
    public void removeArticle(ObjectId threadid, ObjectId articleid) {
        mongoTemplate.updateFirst(new Query(Criteria.where("threadid").is(threadid)),
                new Update().pull("articles", articleid), Thread.class);
    }
}
