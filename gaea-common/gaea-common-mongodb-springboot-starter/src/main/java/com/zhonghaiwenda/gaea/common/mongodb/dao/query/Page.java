package com.zhonghaiwenda.gaea.common.mongodb.dao.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页信息
 *
 * @author gxz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {

    private long total;
    private List<T> data;
}
