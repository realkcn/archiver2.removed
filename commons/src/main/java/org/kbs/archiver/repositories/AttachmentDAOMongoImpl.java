package org.kbs.archiver.repositories;

import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

/**
 * Created by kcn on 14-8-14.
 */

public class AttachmentDAOMongoImpl implements AttachmentDAO {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public InputStream get(String id) {
        GridFSDBFile file = gridFsTemplate.findOne(
                Query.query(whereFilename().is(id)));
        if (file != null)
            return file.getInputStream();
        return null;
    }

    @Override
    public void put(String id, InputStream data) {
        gridFsTemplate.store(data, id);
    }

    @Override
    public void delete(String id) {
        gridFsTemplate.delete(Query.query(whereFilename().is(id)));
    }

    @Override
    public void clear() {
        gridFsTemplate.delete(Query.query(Criteria.where("")));
    }
}
