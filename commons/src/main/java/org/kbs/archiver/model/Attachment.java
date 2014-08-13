package org.kbs.archiver.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Attachment {
    private String attachmentid;
    private String name;
    private long datasize;

    public String getAttachmentid() {
        return attachmentid;
    }

    public void setAttachmentid(String attachmentid) {
        this.attachmentid = attachmentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDatasize() {
        return datasize;
    }

    public void setDatasize(long datasize) {
        this.datasize = datasize;
    }

}
