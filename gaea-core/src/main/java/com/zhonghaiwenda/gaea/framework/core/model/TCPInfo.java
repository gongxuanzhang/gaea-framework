package com.zhonghaiwenda.gaea.framework.core.model;

/**
 * 协议是 tcp协议相关内容
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface TCPInfo {


    /**
     * SYN标识（TCP协议会话建联标识）
     * @return syn 1/0
     **/
    byte getSyn();


    /**
     * FIN标识（TCP协议会话结束标识）
     * @return fin 1/0
     **/
    byte getFin();


    /**
     * tcp会话是否完整
     * @return tcp会话是否完整
     **/
    default boolean isCompleteSession() {
        return getSyn() == 1 && getFin() == 1;
    }

}
