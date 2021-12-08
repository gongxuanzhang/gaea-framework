package com.zhonghaiwenda.gaea.common.mongodb.dao.base;

import lombok.Data;

import java.time.Instant;

/**
 *
 * 系统相关实体基类。可提供查询映射。
 *
 * @author gongxuanzhang
 */
@Data
public class SysDO extends BaseSimpleDO {

    protected Instant createTime;
    protected Instant updateTime;
    protected String creator;
    protected Boolean isSystem;
    protected String orgLink;


}
