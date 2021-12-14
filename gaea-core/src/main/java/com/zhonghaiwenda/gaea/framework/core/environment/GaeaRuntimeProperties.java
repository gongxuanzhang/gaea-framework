package com.zhonghaiwenda.gaea.framework.core.environment;

import lombok.Data;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public class GaeaRuntimeProperties {

    private int corePoolSize;

    private int maxSize;

    private int keepTimeMin = 10;


}
