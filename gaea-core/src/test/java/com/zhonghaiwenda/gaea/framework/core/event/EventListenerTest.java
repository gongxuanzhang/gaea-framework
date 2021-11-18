package com.zhonghaiwenda.gaea.framework.core.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class EventListenerTest implements ApplicationListener<TestEvent> {

    @Override
    public void onApplicationEvent(TestEvent event) {
        GaeaEventTest.enableListener = true;
        GaeaEventTest.eventAttr = event.getName();
    }
}
