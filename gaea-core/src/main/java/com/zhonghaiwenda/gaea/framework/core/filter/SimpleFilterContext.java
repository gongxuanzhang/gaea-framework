package com.zhonghaiwenda.gaea.framework.core.filter;

import java.util.List;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class SimpleFilterContext<D> implements GaeaFilterContext<D>{

    private final List<GaeaFilter<D>> filters;


    private int counter = 0;

    public SimpleFilterContext(List<GaeaFilter<D>> filters) {
        this.filters = filters;
    }

    @Override
    public List<GaeaFilter<D>> getFilters() {
        return this.filters;
    }

    @Override
    public void doFilter(D data) {
        if (!checkSize()) {
            return;
        }
        GaeaFilter<D> currentFilter = filters.get(counter);
        counter++;
        currentFilter.doFilter(data, this);
    }


    private boolean checkSize() {
        if (this.filters == null) {
            return false;
        }
        return this.counter < this.filters.size();
    }
}
