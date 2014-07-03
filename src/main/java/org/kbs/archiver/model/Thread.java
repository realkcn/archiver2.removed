package org.kbs.archiver.model;

import org.kbs.library.Converter;

import java.util.Date;

/**
 * Created by kcn on 14-6-17.
 */
public class Thread implements ModelHasID {
    private long boardid;

    private String subject;

    private Date posttime;

    private int articlenumber;

    private String author;

    private String lastreply;

    private Date lastposttime;

    private long threadid;

    private String encodingurl;

    private long originid;

    private boolean isvisible = true;

    public static Thread newThread(long threadid, Board board, Article article) {
        Thread thread = new Thread();
        thread.setThreadid(threadid);
        thread.setArticlenumber(1);// 考虑最后再插入
        thread.setAuthor(article.getAuthor());
        thread.setBoardid(board.getBoardid());
        thread.setLastposttime(article.getPosttime());
        thread.setLastreply(article.getAuthor());
        thread.setPosttime(article.getPosttime());
        thread.setSubject(article.getSubject());
        thread.setEncodingurl(Converter.randomEncodingfromlong(threadid));
        thread.setOriginid(article.getOriginid());
        return thread;
        // thread.setThreadid()
    }

    public long getBoardid() {
        return boardid;
    }

    public void setBoardid(long boardid) {
        this.boardid = boardid;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getPosttime() {
        return posttime;
    }

    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }

    public int getArticlenumber() {
        return articlenumber;
    }

    public void setArticlenumber(int articlenumber) {
        this.articlenumber = articlenumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLastreply() {
        return lastreply;
    }

    public void setLastreply(String lastreply) {
        this.lastreply = lastreply;
    }

    public Date getLastposttime() {
        return lastposttime;
    }

    public void setLastposttime(Date lastposttime) {
        this.lastposttime = lastposttime;
    }

    public long getThreadid() {
        return threadid;
    }

    public void setThreadid(long threadid) {
        this.threadid = threadid;
    }

    public String getEncodingurl() {
        return encodingurl;
    }

    public void setEncodingurl(String encodingurl) {
        this.encodingurl = encodingurl;
    }

    public long getOriginid() {
        return originid;
    }

    public void setOriginid(long originid) {
        this.originid = originid;
    }

    public boolean isIsvisible() {
        return isvisible;
    }

    public void setIsvisible(boolean isvisible) {
        this.isvisible = isvisible;
    }

    /**
     * @return thread id
     */
    @Override
    public long getId() {
        return getThreadid();
    }
}
