package com.zhonghaiwenda.gaea.test.dao;

import com.zhonghaiwenda.gaea.framework.core.analysor.PackFactory;
import org.springframework.stereotype.Component;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class TestFactory implements PackFactory<String, TestPro> {

    @Override
    public TestPro pack(String input) {
        return new TestPro();
    }
}
