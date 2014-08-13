package org.kbs.archiver.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kcn on 14-6-17.
 */
@Document
@CompoundIndexes({
        @CompoundIndex(name = "board_idx", def = "{'boardid':1,'lastposttime':-1}")
})
public class Thread {
    List<ObjectId> articles;
    @Id
    private ObjectId threadid;
    private ObjectId boardid;
    private String subject;
    private Date posttime;
    private String author;
    private String lastreply;
    private Date lastposttime;
    private String encodingurl;
    private boolean isvisible = true;

    public List<ObjectId> getArticles() {
        return articles;
    }

    public void setArticles(List<ObjectId> articles) {
        this.articles = articles;
    }

    public void addArticle(ObjectId articleid) {
        this.articles.add(articleid);
    }

    public void removeArticle(ObjectId articleid) {
        this.articles.remove(articleid);
    }

    public String getBoardid() {
        return boardid.toString();
    }

    public void setBoardid(ObjectId boardid) {
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

    public ObjectId getThreadid() {
        return threadid;
    }

    public void setThreadid(ObjectId threadid) {
        this.threadid = threadid;
    }

    public String getEncodingurl() {
        return encodingurl;
    }

    public void setEncodingurl(String encodingurl) {
        this.encodingurl = encodingurl;
    }

    public boolean isIsvisible() {
        return isvisible;
    }

    public void setIsvisible(boolean isvisible) {
        this.isvisible = isvisible;
    }

}
