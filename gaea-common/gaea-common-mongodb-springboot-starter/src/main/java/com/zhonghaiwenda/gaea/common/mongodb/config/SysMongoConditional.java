package com.zhonghaiwenda.gaea.common.mongodb.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class SysMongoConditional implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().containsProperty("gaea.mongodb.sys.host");
    }
}
