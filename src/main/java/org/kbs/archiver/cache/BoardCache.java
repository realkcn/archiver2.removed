package org.kbs.archiver.cache;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.kbs.archiver.dao.BoardDAO;
import org.kbs.archiver.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kcn on 14-6-28.
 */

@Component
public class BoardCache implements BoardDAO {
  private static final Logger LOG = LoggerFactory.getLogger(BoardCache.class);

  public BoardDAO getDBDAO() {
    return dbDAO;
  }

  public void setDBDAO(BoardDAO dbDAO) {
    this.dbDAO = dbDAO;
  }

  private boolean caching=false;
  private BoardDAO dbDAO;

  private List<Board> boards;
  private ArrayList<Board> visibleboardlist=new ArrayList<>();
  private HashMap<Long,Board> idMap=new HashMap<>();
  private HashMap<String,Board> nameMap=new HashMap<>();

  public CacheStatistics getStatistics() {
    return statistics;
  }

  public void setStatistics(CacheStatistics statistics) {
    this.statistics = statistics;
  }

  private CacheStatistics statistics=new CacheStatistics();

  public void expireAll() {
    caching=false;
    initCache();
  }

  public void initCache() {
    synchronized (BoardCache.class) {
      if (!caching) {
        LOG.info("init board cache");
        HashMap<Long,Board> newidMap=new HashMap<>();
        HashMap<String,Board> newnameMap=new HashMap<>();
        ArrayList<Board> newvisibleboardlist=new ArrayList<>();
        List<Board> newboards=dbDAO.selectAll();
        for (Board board : newboards) {
          newidMap.put(board.getBoardid(),board);
          newnameMap.put(board.getName().toLowerCase(),board);
          LOG.debug("put "+ ToStringBuilder.reflectionToString(board));
          if (!board.isIshidden())
            newvisibleboardlist.add(board);
        }
        idMap=newidMap;
        nameMap=newnameMap;
        boards=newboards;
        visibleboardlist=newvisibleboardlist;
        getStatistics().setSize(boards.size());
        caching = true;
      }
    }
  }
  /*
   *
   * @return List of boards
   */
  @Override
  public List<Board> selectAll() {
    if (!caching) {
      initCache();
    }
    return boards;
  }

  @Override
  public List<Board> selectAllVisible() {
    if (!caching)
      initCache();
    return visibleboardlist;
  }

  @Override
  public Board selectByName(String name) {
    if (!caching) {
      initCache();
    }
    Board board=nameMap.get(name.toLowerCase());
    LOG.debug("get board by name '"+name+"'="+ToStringBuilder.reflectionToString(board));
    if (board==null)
      getStatistics().incMissCount();
    else
      getStatistics().incHitCount();
    return board;
  }

  @Override
  public Board selectById(long boardid) {
    if (!caching) {
      initCache();
    }
    Board board=idMap.get(boardid);
    LOG.debug("get board by id "+boardid+"="+ToStringBuilder.reflectionToString(board));
    if (board==null)
      getStatistics().incMissCount();
    else
      getStatistics().incHitCount();
    return board;
  }

  @Override
  public void update(Board board) {
    LOG.debug("update board"+ToStringBuilder.reflectionToString(board));
    dbDAO.update(board);
    getStatistics().incExpiredCount();
    expireAll();
  }
}
