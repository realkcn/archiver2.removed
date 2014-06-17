package org.kbs.archiver.model;

/**
 * Created by kcn on 14-6-17.
 */


public class Board {
  private long boardid;
  private String name;
  private int threads;
  private int articles;
  private boolean ishidden;
  private long lastarticleid;
  private String cname;
  private String groupid;
  private String section;
  private boolean ignored;
  private long lastdeleteid;

  public long getBoardid() {
    return boardid;
  }

  public void setBoardid(long boardid) {
    this.boardid = boardid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getThreads() {
    return threads;
  }

  public void setThreads(int threads) {
    this.threads = threads;
  }

  public int getArticles() {
    return articles;
  }

  public void setArticles(int articles) {
    this.articles = articles;
  }

  public boolean isIshidden() {
    return ishidden;
  }

  public void setIshidden(boolean ishidden) {
    this.ishidden = ishidden;
  }

  public long getLastarticleid() {
    return lastarticleid;
  }

  public void setLastarticleid(long lastarticleid) {
    this.lastarticleid = lastarticleid;
  }

  public String getCname() {
    return cname;
  }

  public void setCname(String cname) {
    this.cname = cname;
  }

  public String getGroupid() {
    return groupid;
  }

  public void setGroupid(String groupid) {
    this.groupid = groupid;
  }

  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public boolean isIgnored() {
    return ignored;
  }

  public void setIgnored(boolean ignored) {
    this.ignored = ignored;
  }

  public long getLastdeleteid() {
    return lastdeleteid;
  }

  public void setLastdeleteid(long lastdeleteid) {
    this.lastdeleteid = lastdeleteid;
  }
}
