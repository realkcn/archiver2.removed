package org.kbs.archiver.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.kbs.archiver.SlowTest;
import org.kbs.archiver.StableTest;
import org.kbs.archiver.UnstableTest;
import org.kbs.archiver.cache.BoardCache;
import org.kbs.archiver.model.Board;
import org.kbs.archiver.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class BoardDAOTest {
    @Resource(name = "boardDAO")
    private BoardDAO boardDAO;

    @Resource
    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() throws Exception {
        if (boardDAO.count()>5) {
            fail("Board count greater than five.Are you sure it's database for test?");
        }
        mongoTemplate.dropCollection(Board.class);
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
        boardDAO.save(testBoard);

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
        boardDAO.save(testBoard);

        testBoard.setBoardid(null);
        testBoard.setArticles(2860);
        testBoard.setCname("隐藏版面");
        testBoard.setGroupid("0");
        testBoard.setIgnored(false);
        testBoard.setIshidden(true);
        testBoard.setLastarticleid(0);
        testBoard.setLastdeleteid(0);
        testBoard.setName("隐藏版面");
        testBoard.setSection("站务");
        testBoard.setThreads(11744);
        boardDAO.save(testBoard);
    }

    @Test
    @Category(StableTest.class)
    public void testCacheSelectAllDao() {
        assertEquals(boardDAO.count(), 3);
        assertEquals(boardDAO.countVisible(), 3);
    }

    @Test
    @Category(UnstableTest.class)
    public void testUpdate() {
        String boardname = "Test";
        Board oldboard = boardDAO.findByName(boardname);

        assertEquals("测试专用版面", oldboard.getCname());
        assertEquals("站务", oldboard.getSection());
        Board newboard1 = new Board(oldboard);
        newboard1.setArticles(oldboard.getArticles() + 1);
        boardDAO.save(newboard1);

        Board newboard = boardDAO.findByName(boardname);
        assertEquals("测试专用版面", newboard.getCname());
        assertEquals("站务", newboard.getSection());
        assertEquals(oldboard.getArticles() + 1, newboard.getArticles());
        boardDAO.save(oldboard);

        newboard = boardDAO.findByName(boardname);
        assertEquals(oldboard.getArticles(), newboard.getArticles());
    }

    @Test
    @Category(StableTest.class)
    public void testSelectDao() {
        try {
            String boardname = "Test";

            assertNull(boardDAO.findByName("failddd"));

            // test get board by name
            Board board = boardDAO.findByName(boardname);
            assertEquals(board.getName(), "Test");
            boardDAO.findByName(boardname);

            // test get board by id
            boardname = "Announce";
            Board board1 = boardDAO.findByName(boardname);
            board = boardDAO.findById(board1.getBoardid());
            assertEquals(board.getName(), "Announce");
            boardDAO.findById(board1.getBoardid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed!");
        }
    }

//    @Autowired
//    org.quartz.Scheduler schedulerFactory;
//
//    @Autowired
//    org.quartz.JobDetail boardCacheExpireJob;
//
//    boolean stopRunning;
//
//    AtomicLong count = new AtomicLong(0);
//
//    public class Task implements Runnable {
//
//        @Override
//        public void run() {
//            while (!stopRunning) {
//                testSelectDao();
//                count.incrementAndGet();
//            }
//        }
//    }
//
//    @Category({ SlowTest.class })
//    @Test
//    public void testScheduleExpired() throws Exception {
//        long currenttime = System.currentTimeMillis();
//        org.quartz.Trigger trigger = org.quartz.TriggerUtils.makeSecondlyTrigger("trigger2", 2,
//                org.quartz.SimpleTrigger.REPEAT_INDEFINITELY);
//        schedulerFactory.scheduleJob(boardCacheExpireJob, trigger);
//        ExecutorService executorService = Executors.newFixedThreadPool(100);
//
//        for (int i = 0; i < 100; i++) {
//            Task task = new Task();
//            executorService.execute(task);
//        }
//        stopRunning = false;
//        while (currenttime + 10000 > System.currentTimeMillis()) {
//            Thread.sleep(1000);
//        }
//        stopRunning = true;
//        executorService.shutdown();
//
//        System.out.println("Run Count:" + count);
//        long hitcount = ((BoardCache) boardDAO).getStatistics().getHitCount();
//        long misscount = ((BoardCache) boardDAO).getStatistics().getMissCount();
//        System.out.println("Cache Hit Count:" + hitcount);
//        System.out.println("Cache Miss Count:" + misscount);
//        System.out.println("Cache Hit Rate:" + ((double) hitcount) / (misscount + hitcount));
//        System.out.println("Cache Query Count:" + (misscount + hitcount));
//        assertEquals(count.longValue() * 5, misscount + hitcount);
//    }
}