package com.zhonghaiwenda.gaea.framework.core.adapter;

import com.zhonghaiwenda.gaea.framework.core.ResourceAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zhonghaiwenda.gaea.framework.core.adapter.GaeaResourceAdapt.QUALIFIER_NAME;

/**
 *
 *
 * 加入此注解的实现类 同时实现了 {@link ResourceAdapter}接口
 * 将会被加入到最终解析的适配器中生效
 * @author gxz gongxuanzhang@foxmail.com
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Qualifier(QUALIFIER_NAME)
public @interface GaeaResourceAdapt {

    String QUALIFIER_NAME = "resource_adapt";

}
