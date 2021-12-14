package com.zhonghaiwenda.gaea.test;

import com.zhonghaiwenda.gaea.common.minio.component.MinioTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class TestApplicationTest {

    @Autowired
    private MinioTemplate template;

    @Test
    public void addFile() throws FileNotFoundException {
       template.uploadObject("qujing",new File("/Users/gongxuanzhang/Desktop/tincery_pro.cer.json"));
    }
    @Test
    public void deleteBucket() throws FileNotFoundException {
       template.deleteBucket("asdf");
    }

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









}
