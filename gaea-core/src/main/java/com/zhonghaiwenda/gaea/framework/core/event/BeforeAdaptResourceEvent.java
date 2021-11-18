package com.zhonghaiwenda.gaea.framework.core.event;

/**
 * 在Receive 解析数据源之前的事件
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class BeforeAdaptResourceEvent<R> extends GaeaEvent{


    /**
     * @param source 执行器的参数对象
     **/
    public BeforeAdaptResourceEvent(R source) {
        super(source);
    }
}
