package com.zhonghaiwenda.gaea.framework.core.event;

import lombok.Getter;

/**
 * 在Receive 适配数据源之后的事件
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class PostAdaptResourceEvent<R, I> extends GaeaEvent {



    @Getter
    private final I adapt;

    /**
     * @param source 执行器的参数对象
     * @param ad     解析之后的数据
     **/
    public PostAdaptResourceEvent(R source, I ad) {
        super(source);
        this.adapt = ad;
    }
}
