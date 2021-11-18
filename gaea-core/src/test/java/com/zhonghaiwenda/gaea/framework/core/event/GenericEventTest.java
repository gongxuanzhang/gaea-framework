package com.zhonghaiwenda.gaea.framework.core.event;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class GenericEventTest<R> extends GaeaEvent{

    public GenericEventTest(R source) {
        super(source);
    }
}
