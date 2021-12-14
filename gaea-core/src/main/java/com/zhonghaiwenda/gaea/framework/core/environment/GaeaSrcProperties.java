package com.zhonghaiwenda.gaea.framework.core.environment;

import lombok.Data;

/**
 *
 * Src配置
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class GaeaSrcProperties {

    /**
     *
     * 如果多线程环境下，一个文件切分成几份执行
     **/
    private int subSize = 4;

    /**
     *
     * 当文件低于此配置时，将强制用单线程执行
     **/
    private int forceSize = 2000;


}
