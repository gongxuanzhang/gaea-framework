package com.zhonghaiwenda.gaea.common.mongodb.dao.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author gxz
 * @date 2019/11/20 16:22
 **/
@Setter
@Getter
public class ChildrenData implements Serializable {

    private String childrenOperator;

    private List<SearchData> childrenSearchData;

    /***
     * 把childrenData拼装成List的Criteria  每个Criteria都是一个or或者and的组合条件
     * 注: 这个方法没有过滤空value  需要在传参之前自行过滤
     * @param childrenData 条件参数
     * @return 拼装后的条件
     **/
    public static Criteria[] childrenAssemble(List<ChildrenData> childrenData){
        List<Criteria> result = new ArrayList<>(childrenData.size());
        for (ChildrenData childrenDatum : childrenData) {
            Criteria[] criteria = SearchData.assembleCriteria(childrenDatum.getChildrenSearchData());
            String childrenOperator = childrenDatum.getChildrenOperator();
            //因为在前面校验过  所以在这里只能有and和or的情况
            if(Objects.equals(childrenOperator,"and")){
                result.add(new Criteria().andOperator(criteria));
            }else{
                result.add(new Criteria().orOperator(criteria));
            }
        }
        return result.toArray(new Criteria[]{});
    }
}
