package com.zhonghaiwenda.gaea.test.dao;

import com.zhonghaiwenda.gaea.framework.core.filter.GaeaFilter;
import com.zhonghaiwenda.gaea.framework.core.filter.GaeaFilterContext;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class MyFilter1 implements GaeaFilter<TestPro> {
    @Override
    public void doFilter(TestPro testPro, GaeaFilterContext<TestPro> context) {
        System.out.println("进来1了");
        context.doFilter(testPro);
    }

}
