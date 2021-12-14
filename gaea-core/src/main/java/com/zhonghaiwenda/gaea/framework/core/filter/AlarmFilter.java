package com.zhonghaiwenda.gaea.framework.core.filter;

import com.zhonghaiwenda.gaea.framework.core.model.Alarm;

/**
 * 告警过滤器
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class AlarmFilter<DATA> implements GaeaFilter<DATA> {


    @Override
    public void doFilter(DATA data, GaeaFilterContext<DATA> context) {
        Alarm alarm = createIfNecessary(data);
        if (alarm != null) {
            // todo 告警之后怎么处理
            if (context instanceof AlarmContext) {
                ((AlarmContext) context).appendAlarm(alarm);
            }
        }
        context.doFilter(data);
    }


    /**
     * 如果需要产生告警，返回一个告警内容。
     * 如果不需要产生告警，返回null
     *
     * @param data 过滤数据
     * @return 根据是否产生告警返回
     **/
    protected abstract Alarm createIfNecessary(DATA data);
}
