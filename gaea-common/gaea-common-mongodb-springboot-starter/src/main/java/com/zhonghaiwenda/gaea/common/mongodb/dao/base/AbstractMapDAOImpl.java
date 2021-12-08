package com.zhonghaiwenda.gaea.common.mongodb.dao.base;


import com.zhonghaiwenda.gaea.common.mongodb.dao.query.Page;
import com.zhonghaiwenda.gaea.common.mongodb.dao.query.QueryDTO;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author gxz
 * @date 2019/12/18
 */
public abstract class AbstractMapDAOImpl<T extends Map<String,Object>> implements BaseDAO<T, Query, QueryDTO> {

    protected MongoTemplate mongoTemplate;

    /***此属性不得直接使用 必须用getDbName()方法获取  公共内容已经将getDbName变成了切点*/
    protected String dbName;

    protected abstract Class<T> getClazz();

    public abstract void setDbName(String dbName);

    protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);


    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public String getDbName() {
        return this.dbName;
    }

    @Override
    public void insert(T t) {
        if (insertCheck(t)) {
            mongoTemplate.insert(t, getDbName());
        } else {
            throw new IllegalArgumentException("数据重复 无法插入");
        }
    }


    @Override
    public void insert(List<T> model) {
        for (T t : model) {
            Assert.isTrue(insertCheck(t), t.toString() + "重复异常");
        }
        mongoTemplate.insert(model, getDbName());
    }


    @Override
    public boolean insertCheck(T model) {
        return true;
    }

    @Override
    public boolean updateCheck(T t) {
        return true;
    }

    @Override
    public void saveOrUpdate(T t) {
        mongoTemplate.save(t, getDbName());
    }


    @Override
    public long deleteById(Serializable id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, getDbName()).getDeletedCount();
    }


    @Override
    public long deleteByIds(List<String> ids) {
        Query query = DAOUtils.idsToQuery(ids);
        return mongoTemplate.remove(query, getDbName()).getDeletedCount();
    }

    @Override
    public long update(T t) {
        Update update = DAOUtils.beanToUpdate(t);
        String beanId = DAOUtils.getBeanId(t);
        return mongoTemplate.updateMulti(new Query(Criteria.where("_id").is(beanId)), update, getClazz(), dbName).getModifiedCount();
    }

    @Override
    public long update(Serializable id, Update update) {
        return mongoTemplate.updateMulti(new Query(Criteria.where("_id").is(id)), update, getClazz(), dbName).getModifiedCount();
    }

    @Override
    public long delete(Query query) {
        return mongoTemplate.remove(query, getClazz(), getDbName()).getDeletedCount();
    }


    @Override
    public long update(List<String> ids, T update) {
        Query query = DAOUtils.idsToQuery(ids);
        Update toUpdate = DAOUtils.beanToUpdate(update);
        return mongoTemplate.updateMulti(query, toUpdate, getDbName()).getModifiedCount();
    }

    @Override
    public long update(List<String> ids, Update update) {
        Query query = DAOUtils.idsToQuery(ids);
        return mongoTemplate.updateMulti(query, update, getDbName()).getModifiedCount();
    }

    @Override
    public long update(Query query, Update update) {
        return mongoTemplate.updateMulti(query, update, getClazz(), getDbName()).getModifiedCount();
    }

    @Override
    public long update(T condition, T update) {
        Query query = DAOUtils.beanToQuery(condition);
        Update updateCondition = DAOUtils.beanToUpdate(update);
        return mongoTemplate.updateMulti(query,updateCondition,getDbName()).getModifiedCount();
    }

    @Override
    public T findById(Serializable id) {
        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, this.getClazz(), getDbName());
    }

    @Override
    public List<T> findByIds(Collection<String> ids) {
        Query query = DAOUtils.idsToQuery(ids);
        return mongoTemplate.find(query, this.getClazz(), getDbName());
    }


    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getClazz(), getDbName());
    }


    @Override
    public boolean isExists(Query query) {
        return mongoTemplate.exists(query, getDbName());
    }


    @Override
    public Page<T> findList(Query query, int skip, int limit) {
        long total = mongoTemplate.count(query, this.getClazz(), getDbName());
        query.skip(skip).limit(limit);
        List<T> data = mongoTemplate.find(query, this.getClazz(), getDbName());
        return new Page<>(total, data);
    }

    @Override
    public Page<T> findList(Query query) {
        long total = mongoTemplate.count(query, this.getClazz(), getDbName());
        List<T> data = mongoTemplate.find(query, this.getClazz(), getDbName());
        return new Page<>(total, data);
    }

    @Override
    public Page<T> findList(QueryDTO queryDTO) {
        Query query = queryDTO.toQuery();
        long total = mongoTemplate.count(query, this.getClazz(), getDbName());
        queryDTO.addPage(query);
        List<T> data = mongoTemplate.find(query, this.getClazz(), getDbName());
        return new Page<>(total, data);
    }

    @Override
    public List<T> findListData(Query query) {
        return mongoTemplate.find(query, this.getClazz(), getDbName());
    }

    @Override
    public long findCount(Query query) {
        return mongoTemplate.count(query,getClazz(),getDbName());
    }

    @Override
    public List<T> findListData(QueryDTO queryDTO) {
        Query query = queryDTO.toQuery();
        queryDTO.addPage(query);
        return mongoTemplate.find(query, this.getClazz(), getDbName());
    }

    @Override
    public long findCount(QueryDTO queryDTO) {
        return mongoTemplate.count(queryDTO.toQuery(),getDbName());
    }

    @Override
    public List<T> findListData(Query query, int skip, int limit) {
        query.skip(skip).limit(limit);
        return mongoTemplate.find(query, this.getClazz(), getDbName());
    }


    @Override
    public List<T> findAll() {
        return mongoTemplate.findAll(this.getClazz(), getDbName());
    }





}
