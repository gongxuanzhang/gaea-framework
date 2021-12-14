package com.zhonghaiwenda.gaea.common.minio.exception;

import lombok.Getter;

/**
 * 桶不存在异常
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class BucketCantCreateException extends BucketException {


    @Getter
    private String bucketName;

    public BucketCantCreateException() {
    }

    public BucketCantCreateException(String message) {
        super(message);
    }

    public BucketCantCreateException(String message, String bucketName) {
        super(message);
        this.bucketName = bucketName;
    }

    public BucketCantCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BucketCantCreateException(Throwable cause) {
        super(cause);
    }

    public BucketCantCreateException(String message, Throwable cause, boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
