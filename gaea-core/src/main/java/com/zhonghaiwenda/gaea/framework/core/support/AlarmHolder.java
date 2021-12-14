package com.zhonghaiwenda.gaea.framework.core.support;

import com.zhonghaiwenda.gaea.framework.core.filter.AlarmContext;
import com.zhonghaiwenda.gaea.framework.core.model.Alarm;

import java.util.LinkedList;
import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class AlarmHolder implements AlarmContext {

    private List<Alarm> alarmList;

    @Override
    public List<Alarm> getAlarms() {
        return this.alarmList;
    }

    @Override
    public void appendAlarm(Alarm alarm) {
        if (alarmList == null) {
            alarmList = new LinkedList<>();
        }
        alarmList.add(alarm);
    }
}
