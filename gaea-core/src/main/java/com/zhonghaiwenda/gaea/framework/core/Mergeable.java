package com.zhonghaiwenda.gaea.framework.core;


/**
 *
 * 实现此接口 意味着此类可以进行合并
 * 和{@link Appendable}接口的区别是，此类可以通过{@link Mergeable#unique()}方法获取唯一标识，选取合并对象
 *
 *
 * @see com.zhonghaiwenda.gaea.framework.core.Appendable
 * @see com.zhonghaiwenda.gaea.framework.core.tool.MergeUtils
 * @author gxz gongxuanzhang@foxmail.com
 *
 **/
public interface Mergeable<T> extends Appendable<T> {

    /**
     * 这个方法将会拿到与其他实体区分的内容，也就是说 unique()方法返回值一样，意味着将要合并
     * @return 对象的合并标识
     **/
    String unique();

}
