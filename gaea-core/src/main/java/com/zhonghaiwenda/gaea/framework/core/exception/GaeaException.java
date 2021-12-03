package com.zhonghaiwenda.gaea.framework.core.exception;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
public class GaeaException extends RuntimeException{


    public GaeaException() {
    }

    public GaeaException(String message) {
        super(message);
    }

    public GaeaException(Throwable cause) {
        super(cause);
    }
}
