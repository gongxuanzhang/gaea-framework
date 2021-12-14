package com.zhonghaiwenda.gaea.framework.core.support;

import com.zhonghaiwenda.gaea.framework.core.environment.GaeaRuntimeProperties;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class ThreadPoolFactory {


    private static volatile ExecutorService threadPoolInstance = null;

    public static ExecutorService createThreadPool(GaeaRuntimeProperties runtimeProperties) {

        if (threadPoolInstance == null) {
            synchronized (ThreadPoolFactory.class) {
                if (threadPoolInstance == null) {
                    threadPoolInstance = createThreadPoolByConfig(runtimeProperties);
                }
            }
        }
        return threadPoolInstance;


    }

    private static ExecutorService createThreadPoolByConfig(GaeaRuntimeProperties runtimeProperties) {
        int cpu = Runtime.getRuntime().availableProcessors();
        int corePoolSize = runtimeProperties.getCorePoolSize();
        if (corePoolSize == 0) {
            corePoolSize = cpu + 1;
        }
        int maxSize = runtimeProperties.getMaxSize();
        if (maxSize == 0) {
            maxSize = cpu * 2;
        }

        return new ThreadPoolExecutor(
                corePoolSize,
                maxSize,
                runtimeProperties.getKeepTimeMin(),
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(4096),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
