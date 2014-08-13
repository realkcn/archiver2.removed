package org.kbs.archiver;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.kbs.archiver.SetupData;
import org.kbs.archiver.SlowTest;
import org.kbs.archiver.StableTest;
import org.kbs.archiver.UnstableTest;
import org.kbs.archiver.cache.BoardCache;
import org.kbs.archiver.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
public class BoardDAOTest {
    @Autowired
    org.quartz.Scheduler schedulerFactory;
    @Autowired
    org.quartz.JobDetail boardCacheExpireJob;
    boolean stopRunning;
    AtomicLong count = new AtomicLong(0);
    @Resource
    private BoardCache boardCache;
    @Resource
    private SetupData setupData;

    @Before
    public void setUp() throws Exception {
        setupData.SetupBoard();
    }

    @Test
    @Category(StableTest.class)
    public void testCacheSelectAllDao() {
        assertEquals(boardCache.count(), 3);
        assertEquals(boardCache.countVisible(), 2);
    }

    @Test
    @Category(UnstableTest.class)
    public void testUpdate() {
        String boardname = "Test";
        Board oldboard = new Board(boardCache.findByName(boardname));

        assertEquals("测试专用版面", oldboard.getCname());
        assertEquals("站务", oldboard.getSection());
        Board newboard1 = new Board(oldboard);
        newboard1.setArticles(oldboard.getArticles() + 1);
        boardCache.save(newboard1);

        Board newboard = boardCache.findByName(boardname);
        assertEquals("测试专用版面", newboard.getCname());
        assertEquals("站务", newboard.getSection());
        assertEquals(oldboard.getArticles() + 1, newboard.getArticles());
        boardCache.save(oldboard);

        newboard = boardCache.findByName(boardname);
        assertEquals(oldboard.getArticles(), newboard.getArticles());
    }

    @Test
    @Category(StableTest.class)
    public void testSelectDao() {
        try {
            String boardname = "Test";

            assertNull(boardCache.findByName("failddd"));

            // test get board by name
            Board board = boardCache.findByName(boardname);
            assertEquals(board.getName(), "Test");
            boardCache.findByName(boardname);

            // test get board by id
            boardname = "Announce";
            Board board1 = boardCache.findByName(boardname);
            board = boardCache.findByBoardid(board1.getBoardid());
            assertEquals(board.getName(), "Announce");
            boardCache.findByBoardid(board1.getBoardid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed!");
        }
//        assertEquals( ((BoardCache) boardDAO).getStatistics().getHitCount()
//                +  ((BoardCache) boardDAO).getStatistics().getMissCount(),6);
    }

    @Category({SlowTest.class})
    @Test
    public void testScheduleExpired() throws Exception {
        long currenttime = System.currentTimeMillis();
        //schedulerFactory.scheduleJob(boardCacheExpireJob, trigger);
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        long savehitcount = boardCache.getStatistics().getHitCount();
        long savemisscount = boardCache.getStatistics().getMissCount();

        for (int i = 0; i < 100; i++) {
            Task task = new Task();
            executorService.execute(task);
        }
        stopRunning = false;
        while (currenttime + 10000 > System.currentTimeMillis()) {
            Thread.sleep(1000);
        }
        stopRunning = true;
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.print("Execute Time Out!");
        }

        System.out.println("Run Count:" + count);
        long hitcount = boardCache.getStatistics().getHitCount() - savehitcount;
        long misscount = boardCache.getStatistics().getMissCount() - savemisscount;
        System.out.println("Cache Hit Count:" + hitcount);
        System.out.println("Cache Miss Count:" + misscount);
        System.out.println("Cache Hit Rate:" + ((double) hitcount) / (misscount + hitcount));
        System.out.println("Cache Query Count:" + (misscount + hitcount));
        assertEquals(count.longValue() * 6, misscount + hitcount);


        //Do schedule test
        org.quartz.Trigger trigger = org.quartz.TriggerUtils.makeSecondlyTrigger("trigger2", 2,
                org.quartz.SimpleTrigger.REPEAT_INDEFINITELY);
        schedulerFactory.scheduleJob(boardCacheExpireJob, trigger);

        for (int i = 0; i < 100; i++) {
            Task task = new Task();
            executorService.execute(task);
        }
        stopRunning = false;
        while (currenttime + 10000 > System.currentTimeMillis()) {
            Thread.sleep(1000);
        }
        stopRunning = true;
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.print("Execute Time Out!");
        }
    }

    public class Task implements Runnable {
        @Override
        public void run() {
            while (!stopRunning) {
                testSelectDao();
                count.incrementAndGet();
            }
        }
    }
}