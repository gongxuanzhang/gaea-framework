package com.zhonghaiwenda.gaea.framework.core.event;

/**
 * 在Receive analysis之前的方法
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class BeforeAnalysisEvent<R> extends GaeaEvent{


    /**
     * @param source 真正解析的对象(适配之后的)
     **/
    public BeforeAnalysisEvent(R source) {
        super(source);
    }
}
