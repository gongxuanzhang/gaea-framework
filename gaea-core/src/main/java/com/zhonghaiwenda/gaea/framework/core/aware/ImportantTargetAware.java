package com.zhonghaiwenda.gaea.framework.core.aware;

import org.springframework.beans.factory.Aware;
import org.springframework.lang.Nullable;

/**
 * 此类的子类可以感知是否是重点目标
 * 继承自Spring factory 中的Aware感知接口，表示此类子类也是具有感知功能。
 * 但不具有Spring Aware原本的生命周期回调方法  注意区别
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ImportantTargetAware extends Aware {


    /**
     * 是否是重点目标
     **/
    boolean isImportant();

    /**
     * 如果{@link this#isImportant()} 为true 应该返回important对应的id
     * 否则返回null
     *
     * @return id or null
     **/
    @Nullable
    String getImportantId();


}
