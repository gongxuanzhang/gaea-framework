package com.zhonghaiwenda.gaea.framework.core.tool;


import com.zhonghaiwenda.gaea.framework.core.Appendable;
import com.zhonghaiwenda.gaea.framework.core.Mergeable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 合并相关辅助类
 *
 * @author gongxuanzhang
 * @see Mergeable
 * @see com.zhonghaiwenda.gaea.framework.core.Appendable
 */
public class MergeUtils {


    /**
     * 挑选一个非空对象备用
     *
     * @param obj1 object1
     * @param obj2 object2
     * @return 返回非空对象，如果两个对象都为空则返回null
     **/
    public static <T> T chooseNotNull(T obj1, T obj2) {
        return null == obj1 ? obj2 : obj1;
    }


    //  合并单一集合

    /**
     * 合并单一集合，合并unique相同的所有对象，添加到集合中
     *
     * @param collection 泛型支持合并的集合，可以为null
     * @return 合并之后的集合，如果数据源中没有元素，返回空集合
     **/
    public static <T extends Mergeable<T>> List<T> mergeCollection(Collection<T> collection) {
        return mergeCollection(collection,Mergeable::unique);
    }

    /**
     * 合并单一集合，合并unique相同的所有对象，添加到Map中
     * 返回的Map key是unique value 是合并之后的内容
     *
     * @param collection 泛型支持合并的集合，可以为null
     * @return 合并之后的map  如果数据源中没有元素，返回空Map
     **/
    public static <T extends Mergeable<T>> Map<String, T> mergeCollectionToMap(Collection<T> collection) {
        return mergeCollectionToMap(collection,Mergeable::unique);
    }


    /**
     * 合并单一集合，功能同 {@link MergeUtils#mergeCollection(Collection)}
     * 拓展功能：可以指定如何获取合并unique
     * 手动指定了getkey的方法，即使元素实现了Mergeable也会使用参数中的getkey
     *
     * @param collection 被合并的集合
     * @param getKey     获取unique的方法,不能为null
     * @return 合并之后的集合，如果数据源中没有元素，返回空集合
     **/
    public static <T extends Mergeable<T>> List<T> mergeCollection(Collection<T> collection,
                                                                   Function<T, String> getKey) {
        return mergeCollection(collection,null,getKey,Mergeable::merge);
    }

    /**
     * 合并单一集合，合并unique相同的所有对象，添加到Map中 功能同{@link MergeUtils#mergeCollectionToMap(Collection)}
     * 返回的Map key是unique value 是合并之后的内容
     * 手动指定了getkey的方法，即使元素实现了Mergeable也会使用参数中的getkey
     *
     * @param collection 泛型支持合并的集合，可以为null
     * @return 合并之后的map  如果数据源中没有元素，返回空Map
     **/
    public static <T extends Mergeable<T>> Map<String, T> mergeCollectionToMap(Collection<T> collection, Function<T,
            String> getKey) {
        return mergeCollectionToMap(collection,null,getKey,Mergeable::merge);
    }


    /**
     * 合并单一集合，功能同 {@link MergeUtils#mergeCollection(Collection, Function)}
     * 拓展功能：可以指定如何合并，同时泛型不需要实现Mergeable和Appendable接口
     *
     * @param collection    被合并的集合
     * @param getKey        获取unique的方法,不能为null
     * @param mergeFunction 如何合并
     * @return 合并之后的集合，如果数据源中没有元素，返回空集合
     **/
    public static <T> List<T> mergeCollection(Collection<T> collection, Function<T, String> getKey, BiFunction<T, T,
            T> mergeFunction) {
        return mergeCollection(collection,null,getKey,mergeFunction);
    }

    /**
     * 合并单一集合，合并unique相同的所有对象，添加到Map中 功能同{@link MergeUtils#mergeCollectionToMap(Collection, Function)}
     * 返回的Map key是unique value 是合并之后的内容
     * 拓展功能：可以指定如何合并，同时泛型不需要实现Mergeable和Appendable接口
     *
     * @param collection 泛型支持合并的集合，可以为null
     * @return 合并之后的map  如果数据源中没有元素，返回空Map
     **/
    public static <T> Map<String, T> mergeCollectionToMap(Collection<T> collection, Function<T, String> getKey,
                                                          BiFunction<T, T, T> mergeFunction) {
        return mergeCollectionToMap(collection,null,getKey,mergeFunction);
    }


    //  合并两个集合


    /**
     * 把两个可合并的集合合并
     *
     * @param collection1 list1 可以为null
     * @param collection2 list2 可以为null
     * @return 合并之后的集合，如果数据源中没有元素，返回空集合
     **/
    public static <T extends Mergeable<T>> List<T> mergeCollection(Collection<T> collection1,
                                                                   Collection<T> collection2) {
        return mergeCollection(collection1,collection2,Mergeable::unique,Mergeable::merge);
    }


    /**
     * 把两个可合并的集合合并 添加到Map中
     * Map的key 是unique value是合并之后的值
     *
     * @param collection1 list1 可以为null
     * @param collection2 list2 可以为null
     * @return 合并之后的Map，如果数据源中没有元素，返回空Map
     **/
    public static <T extends Mergeable<T>> Map<String, T> mergeCollectionToMap(Collection<T> collection1,
                                                                               Collection<T> collection2) {
        return mergeCollectionToMap(collection1,collection2,Mergeable::unique,Mergeable::merge);
    }


    /**
     * 把两个可合并的集合合并，功能同{@link MergeUtils#mergeCollection(Collection, Collection)}
     * 增强功能：可以指定合并的唯一标识，使用此方法即使对象实现了Mergeable接口也会被覆盖
     *
     * @param collection1 可以为null
     * @param collection2 可以为null
     * @param getKey      获得唯一合并标识的方法
     * @return 合并之后的集合，如果数据源中没有元素，返回空集合
     **/
    public static <T extends Appendable<T>> List<T> mergeCollection(Collection<T> collection1,
                                                                    Collection<T> collection2,
                                                                    Function<T, String> getKey) {
        return mergeCollection(collection1,collection2,getKey,Appendable::merge);
    }

    /**
     * 把两个可合并的集合合并，功能同{@link MergeUtils#mergeCollectionToMap(Collection, Collection)}
     * 增强功能：可以指定合并的唯一标识，使用此方法即使对象实现了Mergeable接口也会被覆盖
     *
     * @param collection1 可以为null
     * @param collection2 可以为null
     * @param getKey      获得唯一合并标识的方法
     * @return 合并之后的Map，如果数据源中没有元素，返回空Map
     **/
    public static <T extends Appendable<T>> Map<String, T> mergeCollectionToMap(Collection<T> collection1,
                                                                                Collection<T> collection2,
                                                                                Function<T, String> getKey) {
            return mergeCollectionToMap(collection1,collection2,getKey,Appendable::merge);
    }


    /**
     * 把两个可合并的集合合并，功能同{@link MergeUtils#mergeCollection(Collection, Collection, Function)}
     * 增强功能：可以指定合并规则，并且泛型类可以不是Mergeable、Appendable的子类
     *
     * @param collection1 可以为null
     * @param collection2 可以为null
     * @param getKey      获得唯一合并标识的方法
     * @param merge       合并方法
     * @return 合并之后的集合，如果数据源中没有元素，返回空集合
     **/
    public static <T> List<T> mergeCollection(Collection<T> collection1, Collection<T> collection2, Function<T,
            String> getKey, BiFunction<T, T, T> merge) {
        return new ArrayList<>(mergeCollectionToMap(collection1, collection2, getKey, merge).values());

    }


    /**
     * 把两个可合并的集合合并成Map
     * Map 的key 可以指定  value是合并之后的值   合并规则可以配置
     *
     * @param collection1 可以为null
     * @param collection2 可以为null
     * @param getKey      获得唯一合并标识的方法
     * @param merge       合并方法
     * @return 合并之后的集合，如果数据源中没有元素，返回空集合
     **/
    public static <T> Map<String, T> mergeCollectionToMap(Collection<T> collection1, Collection<T> collection2,
                                                          Function<T, String> getKey,
                                                          BiFunction<T, T, T> merge) {
        Assert.notNull(getKey, "method getKey() must not null");
        Assert.notNull(merge, "method merge() must not null");
        if (collection1 == null) {
            collection1 = new ArrayList<>();
        }
        if (collection2 == null) {
            collection2 = new ArrayList<>();
        }
        int mapSize = (collection1.size() + collection2.size()) / 4;
        Map<String, T> candidate = new HashMap<>(Math.max(mapSize, 16));
        collection1.forEach((element) -> candidate.merge(getKey.apply(element), element, merge));
        collection2.forEach((element) -> candidate.merge(getKey.apply(element), element, merge));
        return candidate;
    }


}
