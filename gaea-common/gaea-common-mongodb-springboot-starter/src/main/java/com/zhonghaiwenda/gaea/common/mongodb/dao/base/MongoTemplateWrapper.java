package com.zhonghaiwenda.gaea.common.mongodb.dao.base;

import com.mongodb.WriteConcern;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;
import org.springframework.data.mongodb.core.MappedDocument;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.MongoActionOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author gongxuanzhang
 * 一个增强的MongoTemplate 拥有原对象所有的功能  同时增强功能;
 **/
public class MongoTemplateWrapper extends MongoTemplate {

    private WriteConcern writeConcern;

    public MongoTemplateWrapper(MongoTemplate mongoTemplate) {
        super(mongoTemplate.getMongoDatabaseFactory(), mongoTemplate.getConverter());
        try {
            Field writeConcernField = MongoTemplate.class.getDeclaredField("writeConcern");
            writeConcernField.setAccessible(true);
            Object writeConcern = writeConcernField.get(mongoTemplate);
            if (writeConcern instanceof WriteConcern) {
                this.writeConcern = (WriteConcern) writeConcern;
            }
        } catch (IllegalAccessException | NoSuchFieldException ignore) {
        }
    }

    /***
     *  可以直接忽略_id重复的数据
     **/
    public <T> Collection<T> insertAllIgnoreDuplicate(Collection<? extends T> objectsToSave) {
        return super.insertAll(objectsToSave);
    }

    /***
     *  可以直接忽略_id重复的数据
     **/
    public <T> Collection<T> insertAllIgnoreDuplicate(Collection<? extends T> batchToSave, String collectionName) {

        Assert.notNull(batchToSave, "BatchToSave must not be null!");
        Assert.notNull(collectionName, "CollectionName must not be null!");

        return (Collection<T>) doInsertBatch(collectionName, batchToSave, super.getConverter());
    }


    @Override
    protected List<Object> insertDocumentList(final String collectionName,
                                              final List<Document> documents) {
        if (documents.isEmpty()) {
            return Collections.emptyList();
        }


        execute(collectionName, collection -> {

            MongoAction mongoAction = new MongoAction(writeConcern, MongoActionOperation.INSERT_LIST,
                    collectionName, null,
                    null, null);
            WriteConcern writeConcernToUse = prepareWriteConcern(mongoAction);

            if (writeConcernToUse == null) {
                collection.insertMany(documents, new InsertManyOptions().ordered(false));
            } else {
                collection.withWriteConcern(writeConcernToUse).insertMany(documents);
            }

            return null;
        });

        return MappedDocument.toIds(documents);
    }


}
