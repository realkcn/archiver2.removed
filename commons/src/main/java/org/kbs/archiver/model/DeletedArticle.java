package org.kbs.archiver.model;
/**
 * Created by kcn on 15/2/5.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
public class DeletedArticle extends Article {
    private static final Logger LOG = LoggerFactory.getLogger(DeletedArticle.class);

    private Date deletedTime;

    public DeletedArticle(Article originArticle, Date deletedTime) {
        BeanUtils.copyProperties(originArticle, this);
        this.deletedTime = new Date(deletedTime.getTime());
    }

    public Date getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }
}
