package com.zhonghaiwenda.gaea.framework.core.receiver;

import com.zhonghaiwenda.gaea.framework.core.AbstractReceiver;

import javax.jms.Message;
import java.io.File;

/**
 * src 层核心Receiver
 * 接受Message ---> 转换成文件  给Src层解析
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public abstract class AbstractSrcReceiver extends AbstractReceiver<Message, File> {






    @Override
    protected void analysisAcquire(File acquire) {


    }
}
