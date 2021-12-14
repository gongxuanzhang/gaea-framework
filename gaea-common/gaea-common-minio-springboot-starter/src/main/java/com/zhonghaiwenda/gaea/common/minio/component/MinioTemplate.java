package com.zhonghaiwenda.gaea.common.minio.component;

import io.minio.*;
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
import java.util.Random;

/**
 * Minio 模板 封装一套Minio客户端操作
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Slf4j
public class MinioTemplate implements OssOperations<MinioClient> {


    final MinioClient client;


    public MinioTemplate(MinioClient client) {
        this.client = client;
    }


    @Override
    public boolean deleteBucket(String bucketName, boolean force) {
        if (bucketIsExists(bucketName)) {
            if (force) {
                return forceDeleteBucket(bucketName);
            }
        }
        return true;
    }

    @Override
    public boolean deleteObject(String bucketName, String objectName) {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build();

        try {
            client.removeObject(removeObjectArgs);
            return true;
        } catch (Exception e) {
            log.error("桶{},对象{}删除出现问题{}", bucketName, objectName, e.getMessage());
            return false;
        }
    }


    private boolean forceDeleteBucket(String bucketName) {
        int count = 10;
        while (count > 0 && cleanBucket(bucketName)) {
            try {
                Thread.sleep(new Random().nextInt(30));
            } catch (InterruptedException e) {
                count--;
            }
        }
        try {
            this.client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            return true;
        } catch (Exception e) {
            log.error("删除{}桶过程出现问题{}", bucketName, e.getMessage());
            return false;
        }


    }

    @Override
    public boolean uploadObject(String bucketName, File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getAbsolutePath() + "不存在");
        }
        try {
            this.client.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getName())
                            .filename(file.getAbsolutePath())
                            .build());
            return true;
        } catch (Exception e) {
            log.error("文件{}上传到桶{}失败，{}", file.getName(), bucketName, e.getMessage());
            return false;
        }

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
            return true;
        } catch (Exception e) {
            log.error("桶{}上传文件出现问题{}", bucketName, e.getMessage());
            return false;
        }
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
    public boolean cleanBucket(String bucketName) {
        List<Item> items = listBucket(bucketName);
        RemoveBucketArgs remove = RemoveBucketArgs.builder().bucket(bucketName).build();
        if (CollectionUtils.isEmpty(items)) {
            try {
                this.client.removeBucket(remove);
                return true;
            } catch (Exception e) {
                log.error("桶{}删除出现问题{}", bucketName, e.getMessage());
                return false;
            }
        }

        List<DeleteObject> deleteObjectList = new ArrayList<>();
        for (Item item : items) {
            deleteObjectList.add(new DeleteObject(item.objectName()));
        }
        RemoveObjectsArgs removeObjectsArgs =
                RemoveObjectsArgs.builder().bucket(bucketName).objects(deleteObjectList).build();
        Iterable<Result<DeleteError>> result = this.client.removeObjects(removeObjectsArgs);
        boolean success = true;
        for (Result<DeleteError> deleteErrorResult : result) {
            try {
                deleteErrorResult.get();
            } catch (Exception e) {
                log.error("桶{},删除对象出现问题{}", bucketName, e.getMessage());
                success = false;
            }
        }
        if (success) {
            try {
                this.client.removeBucket(remove);
                return true;
            } catch (Exception e) {
                log.error("桶{}删除出现问题{}", bucketName, e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public MinioClient getClient() {
        return this.client;
    }
}