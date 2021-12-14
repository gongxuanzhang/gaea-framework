package com.zhonghaiwenda.gaea.common.mongodb.dao.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author gxz
 * @date 2019/11/20 10:32
 **/
@Setter
@Getter
public class CommonSearch implements Serializable{

    private String operator;

    private List<SearchData> searchData;

    private List<ChildrenData> childrenData;

    private static final String AND = "and";
    private static final String OR = "or";

    public Query assembleQuery(){
        Query query = new Query();
        //过滤空值
        List<SearchData> trimPrimarySearchData = SearchData.trim(getSearchData());
        List<ChildrenData> childrenData = CommonSearch.trimChildrenData(getChildrenData());
        //一级和二级条件分别拼装Criteria
        Criteria[] primaryCriteria = SearchData.assembleCriteria(trimPrimarySearchData);
        Criteria[] childCriteria = ChildrenData.childrenAssemble(childrenData);
        List<Criteria> criteriaList = new ArrayList<>(Arrays.asList(primaryCriteria));
        criteriaList.addAll(Arrays.asList(childCriteria));
        if(!CollectionUtils.isEmpty(criteriaList)){
            Criteria[] criteriaArray = criteriaList.toArray(new Criteria[]{});
            if(Objects.equals(getOperator(),AND)){
                query.addCriteria(new Criteria().andOperator(criteriaArray));
            }else{
                query.addCriteria(new Criteria().orOperator(criteriaArray));
            }
        }
        return query;
    }

    public Criteria assembleCriteria(){
        Criteria result = Criteria.where("asdfghhjkkk").exists(false);
        List<SearchData> trimPrimarySearchData = SearchData.trim(getSearchData());
        List<ChildrenData> childrenData = CommonSearch.trimChildrenData(getChildrenData());
        //一级和二级条件分别拼装Criteria
        Criteria[] primaryCriteria = SearchData.assembleCriteria(trimPrimarySearchData);
        Criteria[] childCriteria = ChildrenData.childrenAssemble(childrenData);
        List<Criteria> criteriaList = new ArrayList<>(Arrays.asList(primaryCriteria));
        criteriaList.addAll(Arrays.asList(childCriteria));
        if(!CollectionUtils.isEmpty(criteriaList)){
            Criteria[] criteriaArray = criteriaList.toArray(new Criteria[]{});
            if(Objects.equals(getOperator(),AND)){
                result.andOperator(criteriaArray);
            }else{
                result.orOperator(criteriaArray);
            }
        }
        return result;
    }

    /***
     * 参数校验
     * @author gxz
     * @param operators 运算符们
     * @return boolean 是否符合标准
     **/
    public static boolean checkOperator(String... operators) {
        return Arrays.stream(operators).allMatch(operator->Objects.equals(AND,operator) || Objects.equals(OR, operator));
    }

    /***
     * 移除条件中空内容
     * 如果没有条件直接删除
     * @author gxz
     * @date 2019/11/20
     * @param childrenData 条件
     * @return 过了之后的内容
     **/
    public static List<ChildrenData> trimChildrenData(List<ChildrenData> childrenData) {
        if(CollectionUtils.isEmpty(childrenData)) {
            return Collections.emptyList();
        }
        List<ChildrenData> result = new ArrayList<>(childrenData.size());
        for (ChildrenData childrenDatum : childrenData) {
            List<SearchData> searchData = SearchData.trim(childrenDatum.getChildrenSearchData());
            if (!CollectionUtils.isEmpty(searchData)) {
                childrenDatum.setChildrenSearchData(searchData);
                result.add(childrenDatum);
            }
        }
        return result;
    }

}
