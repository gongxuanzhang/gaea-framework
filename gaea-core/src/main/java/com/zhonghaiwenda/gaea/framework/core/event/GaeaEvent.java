package com.zhonghaiwenda.gaea.framework.core.event;


import org.springframework.context.ApplicationEvent;

import java.time.Clock;


/**
 *
 * {@link GaeaEvent}Gaea事件继承自Spring标准事件。
 * 也是Java Event的标准实现。
 * Gaea所有子类事件都可以和Spring监听组件兼容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class GaeaEvent extends ApplicationEvent {


    public GaeaEvent(Object source) {
        super(source);
    }

    public GaeaEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
