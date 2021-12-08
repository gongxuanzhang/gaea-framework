package com.zhonghaiwenda.gaea.framework.core.analysor;

/**
 * 分析器顶层接口，负责分析Receive接受过来的数据，API也可以对外提供
 *
 * @author gxz gongxuanzhang@foxmail.com
 *
 * @see LineAnalyser
 **/
@FunctionalInterface
public interface Analyser<IN, OUT> {


    /**
     * 输入源 解析成分析输出结果
     *
     * @param input 输入数据
     * @return 返回输出数据
     **/
    OUT analyse(IN input);


}
