package com.zhonghaiwenda.gaea.common.mongodb.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gongxuanzhang
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "gaea.mongodb")
public class MongoConfigProperties {
    /**
     * 可配置多个mongodb数据库节点
     **/
    MongoNodeConfig sys;
    MongoNodeConfig pro;


}
