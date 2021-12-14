package com.zhonghaiwenda.gaea.framework.core.filter;

import java.util.List;

/**
 *
 * 过滤器上下文，用于过滤器链中拿到数据
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface GaeaFilterContext<D> {

    /**
     *
     * 拿到当前责任链中的所有过滤器
     * @return 返回个啥
     **/
    List<GaeaFilter<D>> getFilters();

    /**
     *
     * 执行下一个过滤
     *
     **/
    void doFilter(D data);



}
