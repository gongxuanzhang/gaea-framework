package com.zhonghaiwenda.gaea.common.minio.component;


/**
 * 可能因为各种问题进行阻塞，但是功能更高级的接口
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface UnsafeOssOperations<C> extends OssOperations<C> {


    /**
     * 强制删除一个桶
     *
     * @param bucketName 桶名称
     * @return 是否强制删除成功
     **/
    boolean deleteBucketForce(String bucketName);

}
