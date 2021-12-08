package com.zhonghaiwenda.gaea.common.mongodb.dao.query;

import com.zhonghaiwenda.gaea.common.mongodb.dao.jsr303.annotation.BoxData;
import lombok.Data;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author gxz
 * @date 2019/11/1 14:42
 * 所有query对象的父类   接受查询条件内容 必须含有skip 和limit
 **/
@Data
@Validated
public class QueryDTO implements Serializable {
    public Integer skip;
    public Integer limit;
    public Sort sort;
    @BoxData
    public CommonSearch commonSearch;

    /***
     * 默认实现是通用查询  如果需要单独逻辑需要自己实现
     **/
    public Query toQuery() {
        Query query = commonSearch == null ? new Query() : commonSearch.assembleQuery();
        withSort(query);
        return query;
    }

    public void addPage(Query query) {
        withSort(query);
        if (skip != null) {
            query.skip(skip);
        }
        if (limit != null) {
            query.limit(limit);
        }

    }

    public void withSort(Query query) {
        if (haveSort()) {
            if (!Objects.equals(sort.getOrder(), Sort.ASC)) {
                query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, sort.getSort()));
            } else {
                query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.ASC
                        , sort.getSort()));
            }
        }
    }

    private boolean haveSort() {
        return sort != null && StringUtils.hasText(sort.getSort());
    }
}
