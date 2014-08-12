package org.kbs.archiver;
/**
 * Created by kcn on 14-8-11.
 */

import org.bson.types.ObjectId;
import org.kbs.archiver.model.*;
import org.kbs.archiver.model.Thread;
import org.kbs.archiver.persistence.BoardMapper;
import org.kbs.archiver.persistence.ThreadMapper;
import org.kbs.archiver.repositories.BoardRepository;
import org.kbs.archiver.repositories.ThreadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component("migrateService")
public class MigrateService {
    private static final Logger LOG = LoggerFactory
            .getLogger(MigrateService.class);
    @Autowired
    private ThreadMapper threadMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private org.kbs.archiver.model.Thread Convert(ThreadEntity oldthread,ObjectId boardid) {
        Thread thread = new Thread();
        if (boardid==null) {
            Board newBoard = boardRepository.findByName(boardMapper.get(oldthread.getBoardid()).getName());
            if (newBoard == null) {
                LOG.error("Can't find board:" + boardMapper.get(oldthread.getBoardid()).getName() + ",migrate board first.");
                return null;
            }
            boardid=newBoard.getBoardid();
        }
        thread.setBoardid(boardid);
        thread.setArticlenumber(oldthread.getArticlenumber());
        thread.setAuthor(oldthread.getAuthor());
        thread.setEncodingurl(oldthread.getEncodingurl());
        thread.setIsvisible(oldthread.isIsvisible());
        thread.setLastposttime(oldthread.getLastposttime());
        thread.setLastreply(oldthread.getLastreply());
        thread.setOriginid(oldthread.getOriginid());
        thread.setPosttime(oldthread.getPosttime());
        thread.setSubject(oldthread.getSubject());
        thread.setThreadid(null);
        return thread;
    }

    public void moveThread(String boardname) {
        long starttime=System.currentTimeMillis();

        BoardEntity boardEntity = boardMapper.getByName(boardname);
        List<ThreadEntity> oldthreads = threadMapper.getThreadsOnBoard(boardEntity.getBoardid());
        List<Thread> newthreads = new ArrayList<>();
        Board board = boardRepository.findByName(boardname);
        if (board == null) {
            LOG.error("Can't find board:" + boardname + ",migrate board first.");
            return;
        }

//        threadRepository.delete("{'boardid':"+newBoard.getBoardid()+"}");
//        mongoTemplate.dropCollection(Thread.class);
//        mongoTemplate.createCollection(Thread.class);
//        mongoTemplate.indexOps(Thread.class).resetIndexCache();
        if (!mongoTemplate.collectionExists(Thread.class)) {
            mongoTemplate.createCollection(Thread.class);
        } else
            mongoTemplate.remove(new Query(Criteria.where("boardid").is(board.getBoardid())),Thread.class);
        for (ThreadEntity oldthread : oldthreads) {
            Thread thread = Convert(oldthread,board.getBoardid());
            newthreads.add(thread);
        }
// use batch insert for performance
//        threadRepository.save(newthreads);
//        if (newthreads.size()>0) {
//            threadRepository.save(newthreads.get(0));
//            mongoTemplate.insert(newthreads.subList(1,newthreads.size()-1), Thread.class);
//        }


        mongoTemplate.insert(newthreads, Thread.class);
//        mongoTemplate.indexOps(Thread.class).resetIndexCache();
        long timespent=System.currentTimeMillis()-starttime;
        LOG.info("Convert thread for board {} end.Toal time {}:{}",boardname,timespent/1000/60,timespent/1000%60);
    }

    public void migrateAll() {

    }
}
