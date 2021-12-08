package com.zhonghaiwenda.gaea.common.mongodb.dao.jsr303.validate;



import com.zhonghaiwenda.gaea.common.mongodb.dao.jsr303.annotation.BoxData;
import com.zhonghaiwenda.gaea.common.mongodb.dao.query.ChildrenData;
import com.zhonghaiwenda.gaea.common.mongodb.dao.query.CommonSearch;
import com.zhonghaiwenda.gaea.common.mongodb.dao.query.SearchData;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author gxz
 * CommonSearch校验
 * @date 2019/11/5 17:36
 **/
public class BoxDataValidator implements ConstraintValidator<BoxData, CommonSearch> {


    @Override
    public void initialize(BoxData constraintAnnotation) {
    }

    /***
     * 校验两个内容
     * 1 operator必须是and 和or其中之一
     * 2 类型转换内容不能异常
     * @author gxz
     * @date  2019/11/21
     * @param
     * @param commonSearch
     * @param constraintValidatorContext
     * @return boolean
     **/
    @Override
    public boolean isValid(CommonSearch commonSearch, ConstraintValidatorContext constraintValidatorContext) {
        if(commonSearch==null){
            return true;
        }
        if (!CommonSearch.checkOperator(commonSearch.getOperator())) {
            return false;
        }
        try {
            if (!CollectionUtils.isEmpty(commonSearch.getSearchData())) {
                for (SearchData searchDatum : commonSearch.getSearchData()) {
                    cast(searchDatum);
                }
            }
            List<ChildrenData> childrenData = commonSearch.getChildrenData();
            if (!CollectionUtils.isEmpty(childrenData)) {
                String[] childrenOperators = childrenData.stream().map(ChildrenData::getChildrenOperator).toArray(String[]::new);
                if (!CommonSearch.checkOperator(childrenOperators)) {
                    return false;
                }
                for (ChildrenData childrenDatum : childrenData) {
                    List<SearchData> childrenSearchData = childrenDatum.getChildrenSearchData();
                    for (SearchData childrenSearchDatum : childrenSearchData) {
                        cast(childrenSearchDatum);
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    private void cast(SearchData searchData) {
        String lowType = searchData.getType().toLowerCase();
        String value = searchData.getValue();
        if (StringUtils.hasText(value)) {
            // 这里就是故意无意转换一下  看是否抛出异常
            switch (lowType) {
                case "int":
                    Integer.valueOf(value);
                    break;
                case "long":
                    Long.valueOf(value);
                    break;
                case "double":
                    Double.valueOf(value);
                    break;
                default:
                    break;
            }
        }

    }

}
