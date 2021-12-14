package com.zhonghaiwenda.gaea.common.minio.component;

import com.zhonghaiwenda.gaea.common.minio.MinioCondition;
import com.zhonghaiwenda.gaea.common.minio.config.MinioConfig;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Configuration
@EnableConfigurationProperties(MinioConfig.class)
@Slf4j
public class MinioTemplateAutoConfigure {


    @Bean
    @Conditional(MinioCondition.class)
    public MinioTemplate minioTemplate(MinioClient minioClient) {
        return new MinioTemplate(minioClient);
    }

    @Bean
    @Conditional(MinioCondition.class)
    public MinioClient minioClient(MinioConfig minioConfig) {
        String endpoint = minioConfig.getProtocol() + "://" + minioConfig.getHost() + ":" + minioConfig.getPort();
        log.info("minio client: " + endpoint);
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(minioConfig.getUsername(), minioConfig.getPassword())
                .build();
    }

}
