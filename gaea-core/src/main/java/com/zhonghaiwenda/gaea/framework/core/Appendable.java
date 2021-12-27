package com.zhonghaiwenda.gaea.framework.core;


/**
 * 实现此接口 意味着此类可以进行合并
 *
 * @author gxz gongxuanzhang@foxmail.com
 * @see com.zhonghaiwenda.gaea.framework.core.Appendable
 * @see com.zhonghaiwenda.gaea.framework.core.tool.MergeUtils
 **/
public interface Appendable<T> {

    /**
     * 两个实体可以进行合并
     *
     * @param that 另一个实体
     * @return 合并之后的内容
     **/
    T merge(T that);

}
