package com.zhonghaiwenda.gaea.framework.core;

import com.zhonghaiwenda.gaea.framework.core.event.BeforeAdaptResourceEvent;
import com.zhonghaiwenda.gaea.framework.core.event.BeforeAnalysisEvent;
import com.zhonghaiwenda.gaea.framework.core.event.PostAdaptResourceEvent;
import com.zhonghaiwenda.gaea.framework.core.event.PostAnalysisEvent;
import com.zhonghaiwenda.gaea.framework.core.tool.TimeLogger;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 *
 *
 * {@link Receiver}接口的直接抽象实现，提供了执行器的解析模板框架，
 * 一般来说通用于状态，子类可以在具体声明周期上进行扩展
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public abstract class BaseReceiver<R,I> implements Receiver<R> , ApplicationEventPublisherAware {


    @Getter
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void receive(R resource) {
        I ad = doAcquire(resource);

        doAnalysisAcquire(ad);

        free(ad);

    }

    /**
     * 输入来源可能是通过不同渠道获得，
     * 可以通过此方法把数据来源转换成自己想要的内容
     * @param resource 数据源
     * @return 适配之后的内容
     **/
    protected abstract I adaptResource(R resource);

    /**
     * 解析数据源的方法
     * @param acquire 适配之后的数据源
     **/
    protected abstract void analysisAcquire(I acquire);


    /**
     *
     * 模板方法，在事件处理完之后执行的逻辑
     * @param acquire 适配之后的数据源
     **/
    protected void free(I acquire){

    }

    /**
     * 适配数据源
     **/
    private I doAcquire(R resource){
        TimeLogger timeLogger = new TimeLogger("转换数据源");
        timeLogger.start();
        applicationEventPublisher.publishEvent(new BeforeAdaptResourceEvent<>(resource));
        I ad = adaptResource(resource);
        applicationEventPublisher.publishEvent(new PostAdaptResourceEvent<>(resource,ad));
        log.info("把数据源[{}],适配成[{}]",resource,ad);
        timeLogger.end();
        return ad;
    }

    private void doAnalysisAcquire(I acquire){
        TimeLogger timeLogger = new TimeLogger("执行解析");
        timeLogger.start();
        applicationEventPublisher.publishEvent(new BeforeAnalysisEvent<>(acquire));
        analysisAcquire(acquire);
        applicationEventPublisher.publishEvent(new PostAnalysisEvent());
        timeLogger.end();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
