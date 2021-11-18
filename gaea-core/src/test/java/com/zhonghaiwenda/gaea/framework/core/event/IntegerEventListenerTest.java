package com.zhonghaiwenda.gaea.framework.core.event;

import org.junit.jupiter.api.Assertions;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class IntegerEventListenerTest implements ApplicationListener<GenericEventTest<Integer>> {


    @Override
    public void onApplicationEvent(GenericEventTest<Integer> event) {
        Assertions.assertTrue(event.getSource() instanceof Integer);
    }
}
