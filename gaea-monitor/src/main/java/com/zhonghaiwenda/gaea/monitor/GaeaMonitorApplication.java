package com.zhonghaiwenda.gaea.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@SpringBootApplication
@EnableAdminServer
public class GaeaMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaeaMonitorApplication.class, args);
    }
}
