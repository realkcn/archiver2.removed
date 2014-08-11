package org.kbs.archiver;
/**
 * Created by kcn on 14-8-11.
 */

import org.kbs.archiver.model.*;
import org.kbs.archiver.model.Thread;
import org.kbs.archiver.persistence.BoardMapper;
import org.kbs.archiver.persistence.ThreadMapper;
import org.kbs.archiver.repositories.BoardRepository;
import org.kbs.archiver.repositories.ThreadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    private org.kbs.archiver.model.Thread Convert(ThreadEntity oldthread) {
        Thread thread = new Thread();
        Board newBoard = boardRepository.findByName(boardMapper.get(oldthread.getBoardid()).getName());
        if (newBoard == null) {
            LOG.error("Can't find board:" + boardMapper.get(oldthread.getBoardid()).getName() + ",migrate board first.");
            return null;
        }
        thread.setBoardid(newBoard.getBoardid());
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
        BoardEntity boardEntity = boardMapper.getByName(boardname);
        List<ThreadEntity> oldthreads = threadMapper.getThreadsOnBoard(boardEntity.getBoardid());
        for (ThreadEntity oldthread : oldthreads) {
            Thread thread = Convert(oldthread);
            threadRepository.save(thread);
        }
    }
}
