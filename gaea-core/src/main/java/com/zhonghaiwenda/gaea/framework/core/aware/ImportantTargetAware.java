package com.zhonghaiwenda.gaea.framework.core.aware;

import org.springframework.lang.Nullable;

/**
 * 此类的子类可以感知是否是重点目标
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ImportantTargetAware extends Aware {


    /**
     * 是否是重点目标
     **/
    boolean isImportant();

    /**
     * 如果{@link #isImportant} 为true 应该返回important对应的id
     * 否则返回null
     *
     * @return id or null
     **/
    @Nullable
    String getImportantId();


}
