package com.zhonghaiwenda.gaea.framework.core.filter;

import com.zhonghaiwenda.gaea.framework.core.model.Alarm;
import com.zhonghaiwenda.gaea.framework.core.model.ProtocolData;

import java.util.LinkedList;
import java.util.List;

/**
 * src解析数据上下文 支持告警添加
 *
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class SrcAlarmFilterContext<P extends ProtocolData> extends SimpleFilterContext<P> implements AlarmContext{


    private List<Alarm> alarmList;

    public SrcAlarmFilterContext(List<GaeaFilter<P>> filters) {
        super(filters);
    }

    @Override
    public List<Alarm> getAlarms() {
        return this.alarmList;
    }

    @Override
    public void appendAlarm(Alarm alarm) {
        if(alarmList == null){
            alarmList = new LinkedList<>();
        }
        alarmList.add(alarm);
    }
}
