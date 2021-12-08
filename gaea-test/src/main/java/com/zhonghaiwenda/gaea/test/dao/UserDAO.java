package com.zhonghaiwenda.gaea.test.dao;

import com.zhonghaiwenda.gaea.common.mongodb.dao.base.AbstractSimpleDAOImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author gxz
 */
@Repository
public class UserDAO extends AbstractSimpleDAOImpl<User> {


    @Override
    protected Class<User> getClazz() {
        return User.class;
    }

    @Override
    @Value("sys_user")
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }


    @Override
    @Resource(name = "sysMongoTemplate")
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


}
