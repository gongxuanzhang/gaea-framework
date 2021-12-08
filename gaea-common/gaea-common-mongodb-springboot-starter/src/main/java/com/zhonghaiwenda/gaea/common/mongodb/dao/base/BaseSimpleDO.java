package com.zhonghaiwenda.gaea.common.mongodb.dao.base;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author gxz
 * @date 2019/12/11 9:49
 **/

@Data
public abstract class BaseSimpleDO implements BaseDO, Serializable {

    @Id
    protected String id;

    @Override
    public void fill() {

    }

}
