package com.zhonghaiwenda.gaea.common.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@ConfigurationProperties("gaea.minio")
@Data
public class MinioConfig {

    private String protocol = "http";
    private String host;
    private int port;
    private String username;
    private String password;


}
