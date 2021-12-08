package com.zhonghaiwenda.gaea.framework.core.filter;

import org.springframework.core.PriorityOrdered;

/**
 * 白名单过滤器
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class WhiteListFilter<DATA> implements GaeaFilter<DATA>, PriorityOrdered {


    @Override
    public void doFilter(DATA data, GaeaFilterContext context) {
        if (inWhiteList(data)) {
            context.breakFilter();
        }
        context.doFilter();
    }

    /**
     * 是否在白名单中
     *
     * @param data 过滤数据
     * @return 是否在白名单中
     **/
    protected abstract boolean inWhiteList(DATA data);


}
