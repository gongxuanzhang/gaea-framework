package com.zhonghaiwenda.gaea.common.mongodb.dao.base;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gongxuanzhang
 */
public class DAOUtils {

    private static final Map<Class<?>, Field> idFieldCache = new ConcurrentHashMap<>();

    private DAOUtils() {

    }

    private final static Set<String> excludeField;

    static {
        excludeField = new HashSet<>(Arrays.asList("_id", "id"));

    }


    /***
     * 通过对象获取对象字段的update对象 不忽略null的内容 覆盖
     * @param t 实体
     * @return 拼装之后的update
     **/
    public static Update beanToUpdateCovered(Object t) {
        Class<?> beanClass = t.getClass();
        List<Field> fieldList = new ArrayList<>();

        while (!beanClass.equals(BaseSimpleDO.class)) {
            fieldList.addAll(Arrays.asList(beanClass.getDeclaredFields()));
            beanClass = beanClass.getSuperclass();
        }
        Update update = new Update();
        for (Field declaredField : fieldList) {
            if (Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            String fieldName = declaredField.getName();
            if (excludeField.contains(fieldName)) {
                continue;
            }
            declaredField.setAccessible(true);
            Class<?> type = declaredField.getType();
            Object fieldValue = null;
            try {
                fieldValue = declaredField.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Object updateValue = type.cast(fieldValue);
            update.set(fieldName, updateValue);
        }
        return update;
    }

    private static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /***
     * 通过对象获取对象字段的update对象 自动忽略null的内容
     * @param t 实体
     * @return org.springframework.data.mongodb.core.query.Update
     **/
    public static Update beanToUpdate(Object t) {
        Field[] allFields = getAllFields(t);
        Update update = new Update();
        for (Field declaredField : allFields) {
            if (Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            String fieldName = declaredField.getName();
            if (excludeField.contains(fieldName)) {
                continue;
            }
            Class<?> type = declaredField.getType();
            declaredField.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = declaredField.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue == null) {
                continue;
            }
            if (type.isPrimitive()){
                update.set(fieldName,fieldValue);
            }else {
                Object updateValue = type.cast(fieldValue);
                update.set(fieldName, updateValue);
            }
        }
        return update;
    }


    /***
     * @param ids ids
     * @return 拼装Query
     **/
    public static Query idsToQuery(Collection<String> ids) {
        return new Query(Criteria.where("_id").in(ids));
    }

    public static Query beanToQuery(Object t) {
        Class<?> beanClass = t.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        Query query = new Query();
        for (Field declaredField : declaredFields) {
            if (Modifier.isStatic(declaredField.getModifiers())) {
                continue;
            }
            String fieldName = declaredField.getName();
            Class<?> type = declaredField.getType();
            declaredField.setAccessible(true);
            Object o;
            try {
                o = declaredField.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return new Query();
            }
            if (o != null) {
                query.addCriteria(Criteria.where(fieldName).is(type.cast(o)));
            }
        }
        return query;
    }

    /***
     * 获取对象的主键(主键字段必须叫_id或id)
     * @param  t 实体类
     * @return 主键值
     **/
    public static String getBeanId(Object t) {
        Class<?> beanClass = t.getClass();
        Field id = null;
        if (idFieldCache.containsKey(beanClass)) {
            id = idFieldCache.get(beanClass);
        }
        if (id == null) {
            id = findFieldId(t);
        }
        Assert.notNull(id, "实体没有主键");

        try {
            idFieldCache.put(beanClass, id);
            Object idValue = id.get(t);
            return idValue == null ? null : idValue.toString();
        } catch (IllegalAccessException ignore) {

        }
        return null;
    }

    private static Field findFieldId(Object t) {
        Class<?> beanClass = t.getClass();

        while (beanClass != Object.class) {
            Field[] declaredFields = beanClass.getDeclaredFields();
            Optional<Field> sureField =
                    Arrays.stream(declaredFields).filter(field -> field.isAnnotationPresent(Id.class)).findFirst();
            if (sureField.isPresent()) {
                return sureField.get();
            }
            sureField =
                    Arrays.stream(declaredFields).filter(field -> excludeField.contains(field.getName())).findFirst();
            if (sureField.isPresent()) {
                return sureField.get();
            }
            beanClass = beanClass.getSuperclass();
        }
        return null;
    }
}
