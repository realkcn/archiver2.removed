package org.kbs.utils;
/**
 * User: kcn
 * Date: 14-8-6
 * Time: 下午5:40
 */

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

public class MongoDBFactoryBean implements FactoryBean<DB> {
    private static final Logger LOG = LoggerFactory.getLogger(MongoDBFactoryBean.class);

    private Mongo mongo;
    private String name;

    @Override
    public DB getObject() throws Exception {
        Assert.notNull(mongo);
        Assert.notNull(name);
        return mongo.getDB(name);
    }

    @Override
    public Class<?> getObjectType() {
        return DB.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Required
    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    @Required
    public void setName(String name) {
        this.name = name;
    }
}
