package com.zhonghaiwenda.gaea.common.minio.component;

import io.minio.errors.ErrorResponseException;

/**
 * 对response的返回值解析  判断具体内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class MinioExceptionSupport {

    private static final String BUCKET_NOT_EXIST = "NoSuchBucket";
    private static final String BUCKET_NOT_EMPTY = "BucketNotEmpty";


    /**
     * 判断错误返回值是不是因为桶不存在
     *
     * @param code 错误码
     * @return 是否因为桶不存在错误
     **/
    public static boolean isBucketNotExist(String code) {
        return BUCKET_NOT_EXIST.equals(code);
    }


    public static boolean isBucketNotExist(Exception e) {
        if (e instanceof ErrorResponseException) {
            return isBucketNotExist(((ErrorResponseException) e));
        }
        return false;
    }

    /**
     * @param e 抛出的异常
     * @return 是否因为桶不存在错误
     **/
    public static boolean isBucketNotExist(ErrorResponseException e) {
        return isBucketNotExist(e.errorResponse().code());
    }


    /**
     * 判断错误返回值是不是因为桶不为空，常用在删除时
     *
     * @param code 错误码
     * @return 是否因为桶不为空
     **/
    public static boolean isBucketNotEmpty(String code) {
        return BUCKET_NOT_EMPTY.equals(code);
    }


    public static boolean isBucketNotEmpty(Exception e) {
        if (e instanceof ErrorResponseException) {
            return isBucketNotEmpty(((ErrorResponseException) e));
        }
        return false;
    }

    /**
     * @param e 抛出的异常
     * @return 是否因为桶不为空错误
     **/
    public static boolean isBucketNotEmpty(ErrorResponseException e) {
        return isBucketNotEmpty(e.errorResponse().code());
    }



}
