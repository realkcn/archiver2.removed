package org.kbs.archiver.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kbs.archiver.model.Board;
import org.kbs.archiver.cache.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
public class BoardDAOTest {
  @Resource(name="boardDAO")
  private BoardDAO boardDAO;

  @Before
  public void setUp() throws Exception
  {
  }

  @Autowired
  CacheUtils cacheUtils;

  @Test
  public void testCacheSelectAllDao()
  {
    assertEquals(boardDAO.selectAll().size(), 2);
  }

  @Test
  public void testUpdate() {
    cacheUtils.setCache("boardCache");
    cacheUtils.removeAll();

    String boardname="test";
    Board oldboard=boardDAO.selectByName(boardname);
    Board newboard1=new Board(oldboard);
    newboard1.setArticles(oldboard.getArticles()+1);
    boardDAO.update(newboard1);

    Board newboard=boardDAO.selectByName(boardname);
    assertEquals(oldboard.getArticles()+1,newboard.getArticles());
    boardDAO.update(oldboard);

    newboard=boardDAO.selectByName(boardname);
    assertEquals(oldboard.getArticles(),newboard.getArticles());
  }

  @Test
  public void testCacheSelectDao()
  {
    try
    {
      String boardname="test";
      cacheUtils.setCache("boardCache");
      cacheUtils.removeAll();

      //miss cache
      cacheUtils.saveStat();
      assertNull(boardDAO.selectByName("failddd"));
      assertEquals(cacheUtils.getStatistics().getMissCount(), cacheUtils.getSaveStatistics().getMissCount()+1);

      //test hit null cache
      cacheUtils.saveStat();
      assertNull(boardDAO.selectByName("failddd"));
      assertEquals(cacheUtils.getStatistics().getHitCount(),cacheUtils.getSaveStatistics().getHitCount()+1);
      assertEquals(cacheUtils.getStatistics().getSize(),1);

      //test get board by name
      Board board=boardDAO.selectByName(boardname);
      assertEquals(board.getName(),"Test");
      cacheUtils.saveStat();
      assertEquals(cacheUtils.getStatistics().getSize(), 2);
      boardDAO.selectByName(boardname);
      assertEquals(cacheUtils.getStatistics().getSize(), 2);
      assertEquals(cacheUtils.getStatistics().getHitCount(),cacheUtils.getSaveStatistics().getHitCount()+1);

      //test get board by id
      long boardid=305;
      board=boardDAO.selectById(boardid);
      assertEquals(board.getName(),"Announce");
      assertEquals(cacheUtils.getStatistics().getSize(),3);
      cacheUtils.saveStat();
      boardDAO.selectById(boardid);
      assertEquals(cacheUtils.getStatistics().getSize(), 3);
      assertEquals(cacheUtils.getStatistics().getHitCount(),cacheUtils.getSaveStatistics().getHitCount()+1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      fail("Test failed!");
    }
  }
}