package com.zhonghaiwenda.gaea.common.mongodb.dao.jsr303.annotation;




import com.zhonghaiwenda.gaea.common.mongodb.dao.jsr303.validate.BoxDataValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * JSR303 校验commonSearch内容(统一的搜索内容)
 *
 * @author gxz
 * @date 2019/11/5
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {BoxDataValidator.class})
public @interface BoxData {
    //提示信息
    String message() default "参数有误";
    Class<?>[] groups() default{};
    Class<?extends Payload>[] payload() default{};
}
