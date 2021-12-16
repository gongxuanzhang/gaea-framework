package com.zhonghaiwenda.gaea.test;

import com.zhonghaiwenda.gaea.common.minio.component.MinioTemplate;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TestApplicationTest {

    @Autowired
    private MinioTemplate template;


    @Test
    @DisplayName("在不存在的桶中上传文件")
    public void uploadObject() throws FileNotFoundException {
        template.uploadObject("suibian",new File("/Users/gongxuanzhang/Desktop/tincery_pro.cer.json"),"zhangsan.json");
    }

    @Test
    @DisplayName("删除一个不存在的文件")
    public void deleteFile(){
        //  桶不存在
        template.deleteObject("asdf","asdf.json");
        //  桶存在 文件不存在
        template.deleteObject("suibian","asdfasdf.sjon");
    }

    @Test
    @DisplayName("删除一个不存在的桶")
    public void deleteNotExistsBucket(){
        template.deleteBucket("asdfasdf");
    }


    @Test
    @DisplayName("删除一个存在的桶，但是有内容")
    public void deleteNotEmptyBucket(){
        template.deleteBucket("suibian");
    }

    @Test
    @DisplayName("强制删除有内容桶")
    public void forceDeleteBucket(){
        template.deleteBucketForce("suibian");
    }


    @Test
    @DisplayName("上传不存在的文件")
    public void uploadNotExistsFile() {
        Assertions.assertThrows(FileNotFoundException.class, () -> {
            template.uploadObject("aaa",new File("asdf"),"ff");
        });

    }


    @Test
    @DisplayName("查看所有桶")
    public void findAllBucket(){
        List<Bucket> allBucket = template.findAllBucket();
        for (Bucket bucket : allBucket) {
            System.out.println(bucket.name());
        }
    }









}
