package com.zhonghaiwenda.gaea.common.minio.exception;

import lombok.Getter;

/**
 * 桶不存在异常
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class BucketNotSuchException extends BucketException {


    @Getter
    private String bucketName;

    public BucketNotSuchException() {
    }

    public BucketNotSuchException(String message) {
        super(message);
    }

    public BucketNotSuchException(String message, String bucketName) {
        super(message);
        this.bucketName = bucketName;
    }

    public BucketNotSuchException(String message, Throwable cause) {
        super(message, cause);
    }

    public BucketNotSuchException(Throwable cause) {
        super(cause);
    }

    public BucketNotSuchException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
