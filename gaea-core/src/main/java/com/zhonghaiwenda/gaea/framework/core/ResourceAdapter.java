package com.zhonghaiwenda.gaea.framework.core;

/**
 *
 * 数据源适配器顶层接口
 * 用来适配数据源
 *
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ResourceAdapter<R,I> {


    /**
     *
     * 适配
     * @param resource 适配之前的内容
     * @return 适配之后的数据
     **/
    I adapt(R resource);


    /**
     *
     * 是否支持适配内容
     **/
    boolean support(Object resource);



}
