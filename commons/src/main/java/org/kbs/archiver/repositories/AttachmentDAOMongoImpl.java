package org.kbs.archiver.repositories;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import static org.springframework.data.mongodb.core.query.Criteria.where;
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
                new Query(where("_id").is(new ObjectId(id))));
        if (file != null)
            return file.getInputStream();
        return null;
    }

    @Override
    public void put(String id, InputStream data) {
        gridFsTemplate.store(data, new ObjectId(id));
    }

    @Override
    public String put(InputStream data) {
        GridFSFile file=gridFsTemplate.store(data, (DBObject) null);
        return file.getId().toString();
    }

    @Override
    public void delete(String id) {
        gridFsTemplate.delete(new Query(where("_id").is(new ObjectId(id))));
    }

    @Override
    public void clear() {
        gridFsTemplate.delete(Query.query(where("")));
    }
}
