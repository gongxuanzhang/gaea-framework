package com.zhonghaiwenda.gaea.framework.core.detect;

/**
 *
 * 检测器，可检测对应内容
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface Detecter<R,D> {



    /**
     *
     * 检测内容
     * @param resource 数据源
     * @return 检测成功，返回具体数据。 检测失败返回null
     **/
    D detect(R resource);
}
