package org.kbs.archiver;/**
 * User: kcn
 * Date: 14-8-11
 * Time: 下午5:32
 */

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kbs.archiver.cache.BoardCache;
import org.kbs.archiver.dao.BoardDAO;
import org.kbs.archiver.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

import static org.junit.Assert.fail;

public class SetupData {
    private static final Logger LOG = LoggerFactory.getLogger(SetupData.class);

    @Resource(name = "boardDAO")
    private BoardDAO boardDAO;

    @Resource
    private MongoTemplate mongoTemplate;

    public void SetupBoard() {
        if (boardDAO.count()>5) {
            fail("Board count greater than five.Are you sure it's database for test?");
        }
        mongoTemplate.dropCollection(Board.class);
        ((BoardCache)boardDAO).expireAll();
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
        testBoard.setName("Hidden");
        testBoard.setSection("站务");
        testBoard.setThreads(11744);
        boardDAO.save(testBoard);

    }
}
