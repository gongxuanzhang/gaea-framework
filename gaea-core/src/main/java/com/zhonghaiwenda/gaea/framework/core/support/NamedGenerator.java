package com.zhonghaiwenda.gaea.framework.core.support;

/**
 * 命名生成器，子类拥有对命名能力，具体逻辑由子类定义
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface NamedGenerator {


    /**
     *
     * 命名
     * @return 返回逻辑命名内容
     **/
    String generateName();


}
