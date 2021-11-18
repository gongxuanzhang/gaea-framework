package com.zhonghaiwenda.gaea.framework.core.event;

import org.junit.jupiter.api.Assertions;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class StringEventListenerTest implements ApplicationListener<GenericEventTest<String>> {


    @Override
    public void onApplicationEvent(GenericEventTest<String> event) {
        Assertions.assertTrue(event.getSource() instanceof String);
    }
}
