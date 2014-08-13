package org.kbs.archiver.model;
/**
 * Created by kcn on 14-8-12.
 */

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("UnusedDeclaration")
@Document
@CompoundIndexes({
        @CompoundIndex(name = "origin_idx", def = "{'boardid':1,'originid':1}"),
        @CompoundIndex(name = "originfile_idx", def = "{'boardid':1,'filename':1}")
})
public class OriginArticleInfo {
    @Id
    private ObjectId articleid;

    private ObjectId boardid;

    private ObjectId threadid;

    private long originid;

    private String filename;

    private long replyid;

    public long getOriginid() {
        return originid;
    }

    public void setOriginid(long originid) {
        this.originid = originid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getReplyid() {
        return replyid;
    }

    public void setReplyid(long replyid) {
        this.replyid = replyid;
    }

    public ObjectId getArticleid() {
        return articleid;
    }

    public void setArticleid(ObjectId articleid) {
        this.articleid = articleid;
    }

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

}
