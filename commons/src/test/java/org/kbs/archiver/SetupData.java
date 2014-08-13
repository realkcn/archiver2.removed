package org.kbs.archiver;/**
 * User: kcn
 * Date: 14-8-11
 * Time: 下午5:32
 */

import org.kbs.archiver.cache.BoardCache;
import org.kbs.archiver.model.Board;
import org.kbs.archiver.model.Thread;
import org.kbs.archiver.repositories.BoardRepository;
import org.kbs.archiver.repositories.ThreadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.fail;

public class SetupData {
    private static final Logger LOG = LoggerFactory.getLogger(SetupData.class);

    @Resource
    private BoardRepository boardRepository;

    @Resource
    private ThreadRepository threadRepository;

    @Resource
    private BoardCache boardCache;

    @Resource
    private MongoTemplate mongoTemplate;

    public void SetupBoard() {
        if (boardCache.count() > 5) {
            fail("Board count greater than five.Are you sure it's database for test?");
        }
        LOG.info("setup board collection...");
        mongoTemplate.dropCollection(Board.class);
        boardCache.expireAll();
        Board testBoard = new Board();
        testBoard.setArticles(2763);
        testBoard.setCname("站务公告栏");
        testBoard.setGroupid("0");
        testBoard.setIgnored(false);
        testBoard.setIshidden(false);
        testBoard.setLastarticleid(0);
        testBoard.setLastdeleteid(0);
        testBoard.setName("Announce");
        testBoard.setSection("站务");
        testBoard.setThreads(2739);
        boardCache.save(testBoard);

        testBoard.setBoardid(null);
        testBoard.setArticles(28608);
        testBoard.setCname("测试专用版面");
        testBoard.setGroupid("0");
        testBoard.setIgnored(false);
        testBoard.setIshidden(false);
        testBoard.setLastarticleid(0);
        testBoard.setLastdeleteid(0);
        testBoard.setName("Test");
        testBoard.setSection("站务");
        testBoard.setThreads(11744);
        boardCache.save(testBoard);

        testBoard.setBoardid(null);
        testBoard.setArticles(2860);
        testBoard.setCname("隐藏版面");
        testBoard.setGroupid("0");
        testBoard.setIgnored(false);
        testBoard.setIshidden(true);
        testBoard.setLastarticleid(0);
        testBoard.setLastdeleteid(0);
        testBoard.setName("Hidden");
        testBoard.setSection("站务");
        testBoard.setThreads(11744);
        boardCache.save(testBoard);
    }

    public void SetupThread() {
        LOG.info("setup thread collection...");
        mongoTemplate.dropCollection(Thread.class);
        Thread newthread = new Thread();
        newthread.setAuthor("faint");
        newthread.setEncodingurl("fdasfdas");
        newthread.setLastposttime(new Date());
        newthread.setBoardid(boardRepository.findByName("Test").getBoardid());
        threadRepository.save(newthread);
    }
}