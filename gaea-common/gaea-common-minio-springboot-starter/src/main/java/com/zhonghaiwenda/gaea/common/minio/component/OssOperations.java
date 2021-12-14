package com.zhonghaiwenda.gaea.common.minio.component;

import io.minio.messages.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Set;

/**
 * 定义一个OSS 服务器的抽象接口
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface OssOperations<C> {


    /**
     * 删除一个对象
     *
     * @param bucketName 对象的桶名称
     * @param objectName 对象名称
     * @return 是否成功删除 如果对象不存在或者桶不存在返回true
     **/
    boolean deleteObject(String bucketName, String objectName);

    /**
     * 删除一个桶
     *
     * @param bucketName 对象的桶名称
     * @param force      是否强制删除
     * @return 是否删除成功, 如果桶不存在也返回true
     **/
    boolean deleteBucket(String bucketName, boolean force);


    /**
     * 非强制删除一个桶，
     *
     * @return 如果删除成功，或者桶不存在 返回true 如果桶内有对象 返回false
     **/
    default boolean deleteBucket(String bucketName) {
        return deleteBucket(bucketName, false);
    }

    /**
     * 上传文件
     *
     * @param bucketName 桶名称
     * @param file       文件本体
     * @return 是否成功上传，如果文件不存在会抛出异常
     * @throws java.io.FileNotFoundException 当文件不存在时抛出
     **/
    boolean uploadObject(String bucketName, File file) throws FileNotFoundException;


    /**
     * 上传文件
     *
     * @param bucketName 桶名称
     * @param file       文件本体
     * @return 是否成功上传，如果文件不存在会抛出异常
     * @param rename 放在桶里的文件信息,如果不填，默认和上传文件名相同
     * @throws java.io.FileNotFoundException 当文件不存在时抛出
     **/
    boolean uploadObject(String bucketName, File file,String rename) throws FileNotFoundException;


    /**
     * 创建一个桶
     *
     * @param bucketName 桶名称
     * @return 是否创建成功，如果桶存在也返回true
     **/
    boolean createBucket(String bucketName);


    /**
     * 下载一个文件
     *
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @param path       路径，如果路径文件有内容将会报错
     * @return 下载之后的文件
     * @throws java.nio.file.FileAlreadyExistsException 如果文件存在抛出异常
     **/
    File downloadObject(String bucketName, String objectName, String path) throws FileAlreadyExistsException;

    /**
     * 桶是否存在
     *
     * @param bucketName 桶名称
     * @return 桶是否存在
     **/
    boolean bucketIsExists(String bucketName);


    /**
     * 返回桶下面的所有的对象
     *
     * @param bucketName 桶名称
     * @return 如果桶为空，返回空集合，否则返回内容
     **/
    List<Item> listBucket(String bucketName);


    /**
     * 清空桶
     *
     * @param bucketName 桶名字
     * @return 是否清空，如果桶不存在 也返回true
     **/
    boolean cleanBucket(String bucketName);

    C getClient();


}
