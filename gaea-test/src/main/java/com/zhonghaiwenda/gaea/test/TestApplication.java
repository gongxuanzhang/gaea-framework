package com.zhonghaiwenda.gaea.test;

import com.zhonghaiwenda.gaea.common.minio.component.MinioTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@SpringBootApplication
public class TestApplication {


    public static void main(String[] args) throws FileNotFoundException {
        ConfigurableApplicationContext run = SpringApplication.run(TestApplication.class, args);
        MinioTemplate bean = run.getBean(MinioTemplate.class);
        bean.createBucket("asdf");
        bean.uploadObject("asdf",new File("aa.txt"));
    }
}
