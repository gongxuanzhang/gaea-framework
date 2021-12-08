package com.zhonghaiwenda.gaea.framework.core.filter;

/**
 * Gaea过滤器顶层接口，定义统一过滤门面
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/

@FunctionalInterface
public interface GaeaFilter<Data> {

    /**
     * 执行过滤
     * 在过滤器中务必使用 {@link GaeaFilterContext#doFilter()}方法
     * 否则过滤器将不会向下执行
     * 过滤过程中可以对原始数据状态进行改变 或者包装
     *
     * @param data    过滤的数据
     * @param context 过滤器上下文
     **/
    void doFilter(Data data, GaeaFilterContext context);

}
