package com.zhonghaiwenda.gaea.common.mongodb;

import com.zhonghaiwenda.gaea.common.mongodb.config.MongoConfigProperties;
import com.zhonghaiwenda.gaea.common.mongodb.config.MongoNodeConfig;
import com.zhonghaiwenda.gaea.common.mongodb.config.ProMongoConditional;
import com.zhonghaiwenda.gaea.common.mongodb.config.SysMongoConditional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/


@Slf4j
@Configuration
@EnableConfigurationProperties(MongoConfigProperties.class)
public class MongoTemplateAutoConfigure {


    private static final String CLASS_KEY = "_class";


    @Autowired
    private MongoConfigProperties mongoConfigProperties;


    @Autowired
    private MongoMappingContext context;

    @Autowired
    private MongoCustomConversions conversions;


    @Bean
    @Conditional(ProMongoConditional.class)
    @Primary
    public MongoDatabaseFactory proFactory() {
        MongoNodeConfig properties = mongoConfigProperties.getPro();
        log.info("load pro host:[{}],database:[{}]", properties.getServerAddress(), properties.getDatabase());
        return properties.toDbFactory();
    }

    @Bean
    @Conditional(SysMongoConditional.class)
    public MongoDatabaseFactory sysFactory() {
        MongoNodeConfig properties = mongoConfigProperties.getSys();
        log.info("load sys host:[{}],database:[{}]", properties.getServerAddress(), properties.getDatabase());
        return properties.toDbFactory();
    }


    @Bean
    @Conditional(ProMongoConditional.class)
    @Primary
    public MongoTemplate proMongoTemplate() {
        return new MongoTemplate(proFactory(), proMappingMongoConverter());
    }

    @Bean
    @Conditional(SysMongoConditional.class)
    public MongoTemplate sysMongoTemplate() {
        return new MongoTemplate(sysFactory(), sysMappingMongoConverter());
    }


    @Bean
    @Conditional(ProMongoConditional.class)
    @Primary
    MappingMongoConverter proMappingMongoConverter() {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(proFactory());
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);
        MongoNodeConfig sys = mongoConfigProperties.getPro();
        if (sys.isIgnoreClassAttr() && mappingConverter.getTypeMapper().isTypeKey(CLASS_KEY)) {
            mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        }
        return mappingConverter;
    }

    @Bean
    @Conditional(SysMongoConditional.class)
    MappingMongoConverter sysMappingMongoConverter() {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(sysFactory());
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);
        MongoNodeConfig sys = mongoConfigProperties.getSys();
        if (sys.isIgnoreClassAttr() && mappingConverter.getTypeMapper().isTypeKey(CLASS_KEY)) {
            mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        }
        return mappingConverter;
    }


}
