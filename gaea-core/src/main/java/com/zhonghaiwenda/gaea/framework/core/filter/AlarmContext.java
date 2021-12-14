package com.zhonghaiwenda.gaea.framework.core.filter;


import com.zhonghaiwenda.gaea.framework.core.model.Alarm;

import java.util.List;

/**
 * 拥有告警功能的上下文
 * @author gxz gongxuanzhang@foxmail.com
 * @see AlarmFilter
 **/
public interface AlarmContext {


    /**
     *
     * 如果有告警 可以直接从上下文中获取
     *
     * @return 返回个啥
     **/
    List<Alarm> getAlarms();

    /**
     *
     * 添加告警的功能
     * @param alarm 告警内容
     *
     **/
    void appendAlarm(Alarm alarm);

}
