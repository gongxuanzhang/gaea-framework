package com.zhonghaiwenda.gaea.framework.core.adapter;

import com.zhonghaiwenda.gaea.framework.core.exception.GaeaAdaptException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.File;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/

@GaeaResourceAdapt
public class JMSFileResourceAdapter extends FileResourceAdapter<Message> {


    @Override
    public File adapt(Message input) {
        if (input instanceof TextMessage) {
            TextMessage message = (TextMessage) input;
            try {
                String filePath = message.getText();
                return new File(filePath);
            } catch (JMSException e) {
                throw new GaeaAdaptException("适配过程中出现问题，错误信息:" + e.getMessage());
            }
        }
        throw new GaeaAdaptException("不支持的Message类型:" + input.getClass().getName());
    }

    @Override
    public boolean support(Object input) {
        return input instanceof Message;
    }

}
