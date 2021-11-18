package com.zhonghaiwenda.gaea.framework.core;



/**
 *
 * {@link Receiver} 接口是所有执行逻辑的总接口，定义了一个输入资源，和执行逻辑，具体需要由子类拓展
 * @param <R> resource
 * @author gxz gongxuanzhang@foxmail.com
 *
 **/

public interface Receiver<R> {


    /**
     * 执行方法
     **/
    void receive(R resource) ;

}
