package org.kbs.archiver.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by kcn on 14-6-17.
 */
@Document
public class Article {
    @Id
    private ObjectId articleid;

    private ObjectId boardid;

    private ObjectId threadid;

    private String author;

    private Date posttime;

    private int attachment;

    private String subject;

    private boolean isvisible;

    private int datasize;

    private String body;

    public ObjectId getBoardid() {
        return boardid;
    }

    public void setBoardid(ObjectId boardid) {
        this.boardid = boardid;
    }

    public ObjectId getThreadid() {
        return threadid;
    }

    public void setThreadid(ObjectId threadid) {
        this.threadid = threadid;
    }

    public ObjectId getArticleid() {
        return articleid;
    }

    public void setArticleid(ObjectId articleid) {
        this.articleid = articleid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPosttime() {
        return posttime;
    }

    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }

    public int getAttachment() {
        return attachment;
    }

    public void setAttachment(int attachment) {
        this.attachment = attachment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isIsvisible() {
        return isvisible;
    }

    public void setIsvisible(boolean isvisible) {
        this.isvisible = isvisible;
    }

    public int getDatasize() {
        return datasize;
    }

    public void setDatasize(int datasize) {
        this.datasize = datasize;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
