package org.kbs.archiver.service;
/**
 * Created by kcn on 14-7-24.
 */

import org.bson.types.ObjectId;
import org.kbs.archiver.cache.BoardCache;
import org.kbs.archiver.model.Article;
import org.kbs.archiver.model.Board;
import org.kbs.archiver.repositories.ThreadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kbs.archiver.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("UnusedDeclaration")
@Service
public class ThreadService {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadService.class);

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private BoardCache boardCache;

    public Thread newThread(ObjectId threadid, Board board, Article article) {
        Thread thread = new Thread();
        thread.setThreadid(threadid);
        thread.setArticlenumber(1);// 考虑最后再插入
        thread.setAuthor(article.getAuthor());
        thread.setBoardid(board.getBoardid());
        thread.setLastposttime(article.getPosttime());
        thread.setLastreply(article.getAuthor());
        thread.setPosttime(article.getPosttime());
        thread.setSubject(article.getSubject());
        thread.setEncodingurl(threadid.toString());
        thread.setOriginid(article.getOriginid());
        return thread;
        // thread.setThreadid()
    }

    public List<Thread> getThreadByBoard(String boardid, int page, int size) {
        Page<Thread> threads = threadRepository.findByBoardid(boardid,
                new PageRequest(page, size, new Sort(Sort.Direction.DESC, "lastposttime")));
        return threads.getContent();
    }
}
