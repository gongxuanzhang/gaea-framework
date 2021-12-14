package com.zhonghaiwenda.gaea.test;

import com.zhonghaiwenda.gaea.common.minio.component.MinioTemplate;
import com.zhonghaiwenda.gaea.framework.core.Receiver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@SpringBootApplication
public class TestApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TestApplication.class, args);
//        Receiver<File> bean = run.getBean(Receiver.class);
//        bean.receive(new File("/Users/gongxuanzhang/Desktop/cerinfo_1637657925768412.json"));
        MinioTemplate bean = run.getBean(MinioTemplate.class);
        System.out.println(bean.bucketIsExists("zhangsan"));
        System.out.println(bean.createBucket("zhangsan"));
        System.out.println(bean.bucketIsExists("zhangsan"));
    }
}
