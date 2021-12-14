package com.zhonghaiwenda.gaea.framework.core.environment;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Configuration
@EnableConfigurationProperties(GaeaEnvironment.class)
public class GaeaEnvironmentAutoConfiguration {

}
