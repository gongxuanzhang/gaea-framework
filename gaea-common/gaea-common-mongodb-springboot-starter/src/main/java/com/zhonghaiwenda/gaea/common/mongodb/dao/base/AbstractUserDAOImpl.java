package com.zhonghaiwenda.gaea.common.mongodb.dao.base;

import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;

/**
 * @author gxz gongxuanzhang@foxmail.com
 * 这个接口是针对所有用户修改的表内容的实现
 **/
public abstract class AbstractUserDAOImpl<T extends SysDO> extends AbstractSimpleDAOImpl<T>{



    @Override
    @Resource(name = "sysMongoTemplate")
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
            this.mongoTemplate = mongoTemplate;
    }


}
