package com.zhonghaiwenda.gaea.framework.core.receiver;

import cn.hutool.core.collection.ListUtil;
import com.zhonghaiwenda.gaea.framework.core.AbstractReceiver;
import com.zhonghaiwenda.gaea.framework.core.SyncAlternative;
import com.zhonghaiwenda.gaea.framework.core.analysor.LineAnalyser;
import com.zhonghaiwenda.gaea.framework.core.environment.GaeaEnvironment;
import com.zhonghaiwenda.gaea.framework.core.filter.GaeaFilter;
import com.zhonghaiwenda.gaea.framework.core.filter.GaeaFilterContext;
import com.zhonghaiwenda.gaea.framework.core.filter.SimpleFilterContext;
import com.zhonghaiwenda.gaea.framework.core.model.ProtocolData;
import com.zhonghaiwenda.gaea.framework.core.support.ThreadPoolFactory;
import com.zhonghaiwenda.gaea.framework.core.tool.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import java.io.File;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * src 层核心Receiver
 * 接受Message ---> 转换成文件  给Src层解析
 *
 * @param <P> 协议类型数据
 * @author gxz gongxuanzhang@foxmail.com
 **/

public abstract class AbstractSrcReceiver<P extends ProtocolData> extends AbstractReceiver<Message, File> implements SyncAlternative {


    @Autowired(required = false)
    protected LineAnalyser<P> lineAnalyser;


    @Autowired(required = false)
    protected List<GaeaFilter<P>> filters;

    @Autowired
    protected GaeaEnvironment gaeaEnvironment;

    protected volatile ExecutorService threadPoll;

    @Override
    public void setExecutorService(ExecutorService threadPoll) {
        this.threadPoll = threadPoll;
    }

    @Override
    public ExecutorService getExecutorService() {
        if (threadPoll == null) {
            synchronized (this) {
                if (threadPoll == null) {
                    this.threadPoll = ThreadPoolFactory.createThreadPool(gaeaEnvironment.getRuntime());
                }
            }
        }
        return this.threadPoll;
    }


    @Override
    protected void analysisAcquire(File acquire) {
        List<String> lines = FileUtils.readUtf8Lines(acquire);
        int forceSize = this.gaeaEnvironment.getSrc().getForceSize();
        if (lines.size() >= forceSize && shouldSync()) {
            multiThreadAnalysis(lines);
        } else {
            for (String line : lines) {
                lineAnalysis(line);
            }
        }
    }

    /**
     *
     * 多线程异步执行
     * @param lines 被执行的所有文件行
     *
     **/
    private void multiThreadAnalysis(List<String> lines) {
        List<List<String>> partition = ListUtil.partition(lines, this.gaeaEnvironment.getSrc().getSubSize());
        CountDownLatch countDownLatch = new CountDownLatch(partition.size());
        for (List<String> list : partition) {
            getExecutorService().execute(() -> {
                try {
                    for (String line : list) {
                        P analyse = lineAnalyser.analyse(line);
                        GaeaFilterContext<P> filterContext = new SimpleFilterContext<>(filters);
                        filterContext.doFilter(analyse);
                    }
                } finally {
                    countDownLatch.countDown();
                }

            });
        }
        try {
            countDownLatch.await();
            freeSync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void lineAnalysis(String line) {
        P analyse = lineAnalyser.analyse(line);
        GaeaFilterContext<P> filterContext = new SimpleFilterContext<>(filters);
        filterContext.doFilter(analyse);
    }


    /**
     * 如果按照多线程执行，子类可以通过此方法在多线程解析完成之后进行回调
     **/
    protected void freeSync() {

    }


}
