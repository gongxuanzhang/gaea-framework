package com.zhonghaiwenda.gaea.framework.core.handle;

/**
 *
 * 错误处理
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface ErrorHandle {

    /**
     *
     * 当指定内容抛出异常时，将进入此内进行错误处理
     * @param data 异常数据
     * @param e 抛出的异常内容
     **/
    void handle(Object data,Exception e);

}
