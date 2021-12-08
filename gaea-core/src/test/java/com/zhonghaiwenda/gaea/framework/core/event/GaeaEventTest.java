package com.zhonghaiwenda.gaea.framework.core.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class GaeaEventTest  {


    public static boolean enableListener;

    public static String eventAttr;

    public static boolean genericListenerTure;

    public static boolean genericListenerFalse;



    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Test
    @DisplayName("自定义事件监听生效")
    public void aa(){
        String eventAttr = UUID.randomUUID().toString();
        applicationEventPublisher.publishEvent(new TestEvent(eventAttr));
        applicationEventPublisher.publishEvent(new GenericEventTest<>("是字符奥"));
        applicationEventPublisher.publishEvent(new GenericEventTest<>(1));
        assertTrue(enableListener);
        assertEquals(GaeaEventTest.eventAttr,eventAttr);
    }


}
