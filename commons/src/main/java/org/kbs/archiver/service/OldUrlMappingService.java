package org.kbs.archiver.service;
/**
 * Created by kcn on 14-8-23.
 */

import org.bson.types.ObjectId;
import org.kbs.archiver.model.EncodingURLMapping;
import org.kbs.archiver.repositories.EncodingURLMappingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@SuppressWarnings("UnusedDeclaration")
@Service
public class OldUrlMappingService {
    private static final Logger LOG = LoggerFactory.getLogger(OldUrlMappingService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private EncodingURLMappingRepository encodingURLMappingRepository;

    int maxBatchQueue=10000;

    private ArrayList<EncodingURLMapping> mappingList=new ArrayList<>();

    synchronized public void batchAdd(EncodingURLMapping mapping) {
        mappingList.add(mapping);
        if (mappingList.size()>maxBatchQueue)
            batchExecute();
    }

    synchronized public void batchExecute() {
        long start=System.currentTimeMillis();
        mongoTemplate.insert(mappingList, EncodingURLMapping.class);
        long spenttime=System.currentTimeMillis()-start;
        LOG.info("batch insert {} second for {} urlmapping.",spenttime/1000, mappingList.size());
        mappingList.clear();
    }

    public String get(EncodingURLMapping.Type type,String encodingURL) {
        String newEncodingurl=EncodingURLMapping.convertToId(type,encodingURL);
        return encodingURLMappingRepository.findOne(newEncodingurl).getMappingid();
    }
    public ObjectId getArticle(String encodingURL) {
        String id=get(EncodingURLMapping.Type.ARTICLE,encodingURL);
        if (id!=null)
            return new ObjectId(id);
        return null;
    }
    public ObjectId getThread(String encodingURL) {
        String id=get(EncodingURLMapping.Type.THREAD,encodingURL);
        if (id!=null)
            return new ObjectId(id);
        return null;
    }
    public String getAttachment(String encodingURL) {
        return get(EncodingURLMapping.Type.ATTACHMENT,encodingURL);
    }
}
