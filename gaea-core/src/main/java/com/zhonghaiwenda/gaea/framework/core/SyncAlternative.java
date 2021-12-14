package com.zhonghaiwenda.gaea.framework.core;

import org.springframework.lang.Nullable;

import java.util.concurrent.ExecutorService;

/**
 *
 * 此接口的子类可以选择是否由异步执行内容
 * 只有在Gaea框架内的接口才能生效
 * 可生效的接口：
 * @see Receiver
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface SyncAlternative {



    /**
     *
     * 需要异步执行的条件
     * @return 返回是否需要多线程执行，默认为false
     **/
    default boolean shouldSync(){
        return false;
    }

    /**
     *
     * 设置线程池, 如果线程池被提供，在多线程环境下将会使用 {@link #getExecutorService()}的返回值作为执行的线程池
     * @param threadPoll 提供线程池;
     *
     **/
    default void setExecutorService(ExecutorService threadPoll){

    }

    /**
     *
     * 获得线程池 可以返回null，当返回null时，使用框架提供的线程池
     * @return 返回个啥
     **/
    @Nullable
    default ExecutorService getExecutorService(){
        return null;
    }



}
