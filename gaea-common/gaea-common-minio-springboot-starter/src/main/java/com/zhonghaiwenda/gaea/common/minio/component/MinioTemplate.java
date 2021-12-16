package com.zhonghaiwenda.gaea.common.minio.component;

import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

/**
 * Minio 模板 封装一套Minio客户端操作
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Slf4j
public class MinioTemplate implements UnsafeOssOperations<MinioClient> {


    final MinioClient client;


    public MinioTemplate(MinioClient client) {
        this.client = client;
    }

    @Override
    public boolean createBucket(String bucketName) {
        if (bucketIsExists(bucketName)) {
            return true;
        }
        try {
            this.client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            return true;
        } catch (Exception e) {
            log.error("桶{}创建失败{}", bucketName, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean uploadObject(String bucketName, File file) throws FileNotFoundException {
        return uploadObject(bucketName, file, file.getName());
    }

    @Override
    public boolean uploadObject(String bucketName, File file, String rename) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + "不存在");
        }
        try {
            this.client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(rename)
                            .filename(file.getAbsolutePath())
                            .build());
            log.info("上传{}到桶{}中，文件名为{}", file.getAbsolutePath(), bucketName, rename);
            return true;
        } catch (Exception e) {
            if (MinioExceptionSupport.isBucketNotExist(e) &&
                    MinioOperationsSupport.spinCreateBucket(this, bucketName)) {
                uploadObject(bucketName, file, rename);
            } else {
                log.error("桶{}上传文件出现问题{}", bucketName, e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean deleteObject(String bucketName, String objectName) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build();
        try {
            client.removeObject(removeObjectArgs);
            return true;
        } catch (Exception e) {
            if (MinioExceptionSupport.isBucketNotExist(e)) {
                return true;
            }
            log.error("桶{},对象{}删除出现问题{}", bucketName, objectName, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteBucket(String bucketName) {
        if (this.bucketIsExists(bucketName)) {
            try {
                RemoveBucketArgs remove = RemoveBucketArgs.builder().bucket(bucketName).build();
                this.client.removeBucket(remove);
                return true;
            } catch (Exception e) {
                if (MinioExceptionSupport.isBucketNotEmpty(e)) {
                    log.error("桶{}不为空，无法删除", bucketName);
                } else {
                    log.error("桶{}删除出现问题{}", bucketName, e.getMessage());
                }
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean deleteBucketForce(String bucketName) {
        if (this.bucketIsExists(bucketName)) {
            try {
                RemoveBucketArgs remove = RemoveBucketArgs.builder().bucket(bucketName).build();
                this.client.removeBucket(remove);
                return true;
            } catch (Exception e) {
                if (MinioExceptionSupport.isBucketNotEmpty(e)) {
                    //  因为桶不为空所以不能删除
                    List<Item> items = listBucket(bucketName);
                    if (CollectionUtils.isEmpty(items)) {
                        //  如果此时拿到空内容，再次尝试删除桶
                        return deleteBucket(bucketName);
                    }
                    //  尝试清空桶，如果成功 则删除
                    if (cleanBucket(bucketName)) {
                        return deleteBucket(bucketName);
                    }
                    //  清空桶失败，则直接返回false
                    log.error("清空桶{}失败", bucketName);
                    return false;
                } else {
                    log.error("桶{}删除出现问题{}", bucketName, e.getMessage());
                }
                return false;
            }
        }
        return true;
    }



    @Override
    public List<Bucket> findAllBucket() {
        try {
            return this.client.listBuckets();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Item> listBucket(String bucketName) {
        ListObjectsArgs args = ListObjectsArgs.builder().bucket(bucketName).build();
        Iterable<Result<Item>> results = this.client.listObjects(args);
        List<Item> result = new ArrayList<>();
        for (Result<Item> item : results) {
            try {
                result.add(item.get());
            } catch (Exception e) {
                log.error("桶{},获取对象出现问题{}", bucketName, e.getMessage());
            }
        }
        return result;
    }

    @Override
    public File downloadObject(String bucketName, String objectName, String path) throws FileAlreadyExistsException {
        File file = new File(path);
        if (file.exists()) {
            throw new FileAlreadyExistsException(file.getAbsolutePath() + "已经存在");
        }
        DownloadObjectArgs download = DownloadObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .filename(path)
                .build();
        try {
            this.client.downloadObject(download);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean bucketIsExists(String bucketName) {
        try {
            return this.client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("桶{}，获取信息失败{}", bucketName, e.getMessage());
            return false;
        }

    }

    @Override
    public boolean cleanBucket(String bucketName) {
        //  可能其他线程清空了
        List<Item> items = listBucket(bucketName);
        if (CollectionUtils.isEmpty(items)) {
            return true;
        }

        List<DeleteObject> deleteObjectList = new ArrayList<>();
        for (Item item : items) {
            deleteObjectList.add(new DeleteObject(item.objectName()));
        }
        RemoveObjectsArgs roa = RemoveObjectsArgs.builder().bucket(bucketName).objects(deleteObjectList).build();
        Iterable<Result<DeleteError>> result = this.client.removeObjects(roa);
        boolean success = true;
        for (Result<DeleteError> deleteErrorResult : result) {
            try {
                deleteErrorResult.get();
            } catch (Exception e) {
                log.error("桶{},删除对象出现问题{}", bucketName, e.getMessage());
                success = false;
            }
        }
        return success;
    }

    @Override
    public MinioClient getClient() {
        return this.client;
    }

}
