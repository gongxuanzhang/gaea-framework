package com.zhonghaiwenda.gaea.framework.core.tool;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 时间记录器，可以打印一段时间区间的信息
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Slf4j
public class TimeLogger {


    /**
     * 任务名称
     **/
    private final String taskName;

    /**
     * 开始时间
     **/
    private long startTimeMill;


    public TimeLogger(String taskName) {
        this.taskName = taskName;
    }


    public void start(boolean print) {
        this.startTimeMill = System.currentTimeMillis();
        if(print){
            log.info("任务{}开始于{}",this.taskName,formatMill(this.startTimeMill));
        }
    }

    public void start(){
        start(true);
    }


    /**
     * 结束计时
     *
     * @param print 是否打印信息
     **/
    public void end(boolean print) {
        long current = System.currentTimeMillis();
        long totalTimeMill = current - startTimeMill;
        if (print) {
            log.info("{},开始时间{},结束时间{},执行了{}", this.taskName,
                    formatMill(this.startTimeMill),
                    formatMill(current),
                    formatDuration(totalTimeMill)
            );
        }
    }

    public void end() {
        end(true);
    }

    private String formatMill(long time) {
        return DateUtil.format(new Date(time), DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 格式化持续时间
     *
     * @param duration 持续时间毫秒
     * @return 带单位的时间
     **/
    private String formatDuration(long duration) {
        StringBuilder sb = new StringBuilder();
        if (duration >= DateUnit.WEEK.getMillis()) {
            sb.append(duration / DateUnit.WEEK.getMillis()).append("周");
            duration %= DateUnit.WEEK.getMillis();
        }
        if (duration >= DateUnit.DAY.getMillis()) {
            sb.append(duration / DateUnit.DAY.getMillis()).append("天");
            duration %= DateUnit.DAY.getMillis();
        }
        if (duration >= DateUnit.HOUR.getMillis()) {
            sb.append(duration / DateUnit.HOUR.getMillis()).append("小时");
            duration %= DateUnit.HOUR.getMillis();
        }
        if (duration >= DateUnit.MINUTE.getMillis()) {
            sb.append(duration / DateUnit.MINUTE.getMillis()).append("分钟");
            duration %= DateUnit.MINUTE.getMillis();
        }
        if (duration >= DateUnit.SECOND.getMillis()) {
            sb.append(duration / DateUnit.SECOND.getMillis()).append("秒");
            duration %= DateUnit.SECOND.getMillis();
        }
        if (duration >= DateUnit.MS.getMillis()) {
            sb.append(duration / DateUnit.MS.getMillis()).append("毫秒");
        }
        return sb.toString();
    }


}
