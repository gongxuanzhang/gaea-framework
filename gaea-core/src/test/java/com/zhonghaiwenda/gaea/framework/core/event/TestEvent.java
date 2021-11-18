package com.zhonghaiwenda.gaea.framework.core.event;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class TestEvent extends GaeaEvent{


    private final String name;

    public TestEvent(String name) {
        super(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
