package com.zhonghaiwenda.gaea.framework.core.tool;

/**
 * 协议工具类
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class ProcotolUtils {

    private static final int TCP = 6;
    private static final int UDP = 17;


    /**
     * 协议号是否是tcp
     **/
    public static boolean isTCP(int protocol) {
        return TCP == protocol;
    }


    /**
     * 协议号是否是UDP
     **/
    public static boolean isUDP(int protocol) {
        return UDP == protocol;
    }

}
