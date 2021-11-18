package com.zhonghaiwenda.gaea.framework.core.event;

import com.zhonghaiwenda.gaea.framework.core.tool.TimeLogger;

/**
 *
 * 执行超时事件
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class TimeOutEvent extends GaeaEvent{


    public TimeLogger logger;


    public TimeOutEvent(Object source) {
        super(source);
    }
}
