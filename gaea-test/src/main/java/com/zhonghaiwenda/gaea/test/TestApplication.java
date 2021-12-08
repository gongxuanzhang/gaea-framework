package com.zhonghaiwenda.gaea.test;

import com.zhonghaiwenda.gaea.test.dao.User;
import com.zhonghaiwenda.gaea.test.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@SpringBootApplication
public class TestApplication {

    @Autowired
    ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TestApplication.class, args);
        UserDAO bean = run.getBean(UserDAO.class);

    }

    @PostConstruct
    public void init(){

    }
}
