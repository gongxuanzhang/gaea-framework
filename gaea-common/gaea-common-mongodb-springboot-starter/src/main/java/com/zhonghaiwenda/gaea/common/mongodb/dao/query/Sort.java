package com.zhonghaiwenda.gaea.common.mongodb.dao.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 排序信息
 *
 * @author mengxiangyun
 */

@Getter
@Setter
public class Sort {
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	/** 排序字段*/
	private String sort;

	/** 排序方向 asc/desc*/
	private String order = DESC;


}
