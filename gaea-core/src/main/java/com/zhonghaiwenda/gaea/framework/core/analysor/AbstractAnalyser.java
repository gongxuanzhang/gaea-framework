package com.zhonghaiwenda.gaea.framework.core.analysor;


import com.zhonghaiwenda.gaea.framework.core.handle.ErrorHandle;
import com.zhonghaiwenda.gaea.framework.core.handle.LineAnalysisHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 解析器的模板实现，定义了解析的声明周期内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/

public abstract class AbstractAnalyser<IN, OUT> implements Analyser<IN, OUT> {


    protected PackFactory<IN, OUT> factory;

    @Autowired(required = false)
    protected List<LineAnalysisHandle> errorHandle;

    @Override
    public OUT analyse(IN input) {
        try {
            if (factory == null) {
                return pack(input);
            }
            return factory.pack(input);
        } catch (Exception e) {
            errorHandle(input, e);
            return null;
        }
    }

    protected void errorHandle(IN input, Exception e) {
        if (!CollectionUtils.isEmpty(errorHandle)) {
            for (ErrorHandle handle : errorHandle) {
                handle.handle(input, e);
            }
        }
    }


    /**
     * 注入包装工厂，如果此方法没有调用。那子类需要实现pack方法。
     * 否则在解析时会抛出异常
     *
     * @param factory 包装工厂
     **/
    @Autowired(required = false)
    public void setFactory(PackFactory<IN, OUT> factory) {
        this.factory = factory;
    }

    protected OUT pack(IN input) {
        throw new UnsupportedOperationException("没有实现的pack方法，子类需要实现或者在解析器中注入解析工厂");
    }


}
