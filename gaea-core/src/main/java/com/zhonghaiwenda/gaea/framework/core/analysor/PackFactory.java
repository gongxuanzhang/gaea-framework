package com.zhonghaiwenda.gaea.framework.core.analysor;


/**
 * 包装工厂，等效于{@link Analyser}
 * 一般用于解耦使用，作为{@link AbstractAnalyser}声明周期的一环使用。
 * 如果{@link AbstractAnalyser}中注入{@link PackFactory}工厂实现，可以替代声明周期中的pack()方法
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@FunctionalInterface
public interface PackFactory<IN, OUT> {

    /**
     * 等效于{@link AbstractAnalyser#pack(Object)}
     * 包装
     *
     * @param input 属于源
     * @return 工厂生产的内容
     **/
    OUT pack(IN input);


}
