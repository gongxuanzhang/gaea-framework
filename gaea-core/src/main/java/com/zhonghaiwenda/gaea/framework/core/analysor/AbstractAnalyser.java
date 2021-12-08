package com.zhonghaiwenda.gaea.framework.core.analysor;


/**
 * 解析器的模板实现，定义了解析的声明周期内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/

public class AbstractAnalyser<IN, OUT> implements Analyser<IN, OUT> {


    protected PackFactory<IN, OUT> factory;

    @Override
    public OUT analyse(IN input) {
        OUT pack = null;
        if (factory == null) {
            pack = pack(input);
        } else {
            pack = factory.pack(input);
        }


        return null;
    }



    /**
     * 注入包装工厂，如果此方法没有调用。那子类需要实现pack方法。
     * 否则在解析时会抛出异常
     * @param factory 包装工厂
     *
     **/
    public void setFactory(PackFactory<IN, OUT> factory) {
        this.factory = factory;
    }

    protected OUT pack(IN input) {
        throw new UnsupportedOperationException("没有实现的pack方法，子类需要实现或者在解析器中注入解析工厂");
    }


}
