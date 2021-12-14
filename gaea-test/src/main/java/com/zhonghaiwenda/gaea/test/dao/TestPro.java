package com.zhonghaiwenda.gaea.test.dao;

import com.zhonghaiwenda.gaea.framework.core.model.AbstractSessionData;
import com.zhonghaiwenda.gaea.framework.core.model.Protocol;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

public class TestPro extends AbstractSessionData {



    @Override
    public Protocol getProtocolModel() {
        return Protocol.ISAKMPInitiator;
    }
}
