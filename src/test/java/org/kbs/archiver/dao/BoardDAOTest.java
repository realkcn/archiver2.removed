package org.kbs.archiver.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kbs.archiver.model.Board;
import org.kbs.archiver.utils.CacheUtils;
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
    assertEquals(boardDAO.selectAll().size(),2);
  }

  @Test
  public void testCacheSelectDao()
  {
    try
    {
      String boardname="test";
      cacheUtils.setCache("boardCache");
      Board board=boardDAO.select(boardname);
      assertEquals(board.getName(),"Test");
      cacheUtils.saveStat();
      boardDAO.select(boardname);
      assertEquals(cacheUtils.getStatistics().getHitCount(),cacheUtils.getSaveStatistics().getHitCount()+1);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      fail("Test failed!");
    }
  }
}