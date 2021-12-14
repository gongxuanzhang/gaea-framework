package com.zhonghaiwenda.gaea.common.minio.exception;

import lombok.Getter;

/**
 * 桶不为空异常，一般用于删除桶的时候使用
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class BucketNotEmptyException extends BucketException {


    @Getter
    private String bucketName;

    public BucketNotEmptyException() {
    }

    public BucketNotEmptyException(String message) {
        super(message);
    }

    public BucketNotEmptyException(String message, String bucketName) {
        super(message);
        this.bucketName = bucketName;
    }

    public BucketNotEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public BucketNotEmptyException(Throwable cause) {
        super(cause);
    }

    public BucketNotEmptyException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
