package com.zhonghaiwenda.gaea.common.minio.component;

import java.util.Random;

/**
 * 封装一些多线程中 可能有问题的操作类
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MinioOperationsSupport {


    private static final int SPIN_COUNT = 10;

    private static final int RANDOM_SLEEP = 30;

    /**
     * 自旋创建桶
     *
     * @param ossOperations 操作对象
     * @param bucketName    桶名称
     * @return 是否创建成功
     **/
    public static boolean spinCreateBucket(OssOperations<?> ossOperations, String bucketName) {
        for (int i = 0; i < SPIN_COUNT; i++) {
            if (ossOperations.createBucket(bucketName)) {
                return true;
            }
            if (!ossOperations.bucketIsExists(bucketName)) {
                randomSleep(RANDOM_SLEEP);
            } else {
                return true;
            }

        }
        return false;
    }


    /**
     * 随机睡眠一个时间,忽略异常
     *
     * @param sleepTime 睡眠时间，单位毫秒
     **/
    private static void randomSleep(int sleepTime) {
        try {
            Thread.sleep(new Random().nextInt(sleepTime));
        } catch (InterruptedException ignore) {
        }
    }

}
