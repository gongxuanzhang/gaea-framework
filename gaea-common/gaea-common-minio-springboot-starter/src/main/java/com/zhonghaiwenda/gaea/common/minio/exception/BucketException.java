package com.zhonghaiwenda.gaea.common.minio.exception;

import lombok.Getter;

/**
 * 桶异常
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class BucketException extends RuntimeException {


    @Getter
    private String bucketName;

    public BucketException() {
    }

    public BucketException(String message) {
        super(message);
    }

    public BucketException(String message, String bucketName) {
        super(message);
        this.bucketName = bucketName;
    }

    public BucketException(String message, Throwable cause) {
        super(message, cause);
    }

    public BucketException(Throwable cause) {
        super(cause);
    }

    public BucketException(String message, Throwable cause, boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
