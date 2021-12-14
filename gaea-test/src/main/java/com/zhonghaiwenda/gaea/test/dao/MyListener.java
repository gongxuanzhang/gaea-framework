package com.zhonghaiwenda.gaea.test.dao;

import com.zhonghaiwenda.gaea.framework.core.event.GaeaEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class MyListener implements ApplicationListener<GaeaEvent> {


    @Override
    public void onApplicationEvent(GaeaEvent event) {
        System.out.println("gaea事件发生了");
    }
}
