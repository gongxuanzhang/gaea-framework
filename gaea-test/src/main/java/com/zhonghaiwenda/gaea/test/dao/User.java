package com.zhonghaiwenda.gaea.test.dao;

import com.zhonghaiwenda.gaea.common.mongodb.dao.base.BaseSimpleDO;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
public class User extends BaseSimpleDO {

  //  private static Long serialVersionUID = 1L;

    /***用户名称(显示名称)*/
    private String name;

    /***用户名*/
    private String username;


    private List<String> roles;
    /***密码*/
    private String password;
    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态(1:正常 2:锁定)
     */
    private Integer status;
    /***所属机构*/
    private String org;

    private String orgLink;

    private List<String> emailTags;

    private List<String> httpTags;

    private List<String> appTypes;

    private List<String> alarmcasetags;

    private Map<String,Integer> lastView;

    /*@ExcelField(converter = ExcelConvert.class)*/
    private Instant createTime;
    private Instant updateTime;
    private String creatorUser;
    private Boolean delete;

    private Integer wrongCount;
    private Instant wrongTime;

    /**过期时间*/
    private Instant endTime;
}
