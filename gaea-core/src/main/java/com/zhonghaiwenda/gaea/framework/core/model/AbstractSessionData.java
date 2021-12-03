package com.zhonghaiwenda.gaea.framework.core.model;


import lombok.Data;

/**
 * 这是一个抽象的会话实体，表示一个会话中应有的内容
 * todo 整理实体
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Data
public abstract class AbstractSessionData implements ProtocolData {


    /**
     * 探针标识 记录是哪个探针生成的txt
     */
    protected String source;
    /**
     * 数据采集时间
     */
    protected Long captureTime;
    /**
     * 数据会话结束
     */
    protected Long endTime;
    /**
     * 用户标识
     * 按以下优先级唯一确定一个用户身份
     * 1.msisdn 2.imsi 3.imei 4.ADSL (暂无) 5.ip
     */
    protected String userId;

    /**
     * 用户标识类型 标识userId标识的内容是什么
     * 1.msisdn 2.imsi 3.imei 4.ADSL (暂无) 5.ip
     **/
    protected int userIdType;


    /**
     * 服务端Adsl标识
     */
    protected String serverAdsl;

    /**
     * 协议名
     */
    protected String protocolName;

    //  内外层五元组相关

    /**
     * 协议， 6 和17  表示 tcp udp
     **/
    protected Integer protocol;
    protected String clientMac;
    protected String serverMac;
    protected String clientIp;
    protected String serverIp;
    protected Integer clientPort;
    protected Integer serverPort;
    protected String clientIpOuter;
    protected String serverIpOuter;
    protected Integer clientPortOuter;
    protected Integer serverPortOuter;
    protected Integer protocolOuter;

    /**
     * 在伪造协议时上下行字节
     */
    protected String malformedUpPayload;
    protected String malformedDownPayload;
    /**
     * SIM卡唯一识别码
     */
    protected String imsi;
    /**
     * 移动终端唯一识别码
     */
    protected String imei;
    /**
     * 手机号
     */
    protected String msisdn;
    /**
     * dataType数据类型字段
     * session：0：未知proName 1：已知proName
     * dns：-1：伪造 0：请求 1：应答
     * ssl：-1：伪造 0：正常
     * http：-1：伪造 1：正常
     * ssh：-1：伪造 1：正常
     */
    protected Integer dataType;

    /**
     * 流量相关
     */
    protected Long upPkt;
    protected Long upByte;
    protected Long downPkt;
    protected Long downByte;

    /**
     * 该会话是否境外会话
     */
    protected Boolean foreign;

    /**
     * 该会话是否加密
     */
    protected Boolean encryption;


    /**
     * 输入线路名称
     */
    protected String inputLine;


}
