package com.zhonghaiwenda.gaea.common.mongodb.dao.query;

import com.zhonghaiwenda.gaea.common.mongodb.tool.ParamUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author gxz
 * @date 2019/11/20 10:39
 **/
@Setter
@Getter
public class SearchData implements Serializable {


    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    private String collName;
    private Integer operator;
    private String type;
    private String value;

    /***
     * 清除所有value为空的searchData
     * @param searchData 条件
     * @return 过滤之后的内容
     **/
    public static List<SearchData> trim(List<SearchData> searchData){
        return searchData.stream().filter(SearchData::isNotEmpty).collect(Collectors.toList());
    }


    /**
     * 判断词条记录是否被看作一个空值
     * 如果没有值  不过滤 boolean和 exists
     * @return boolean
     **/
    private boolean isNotEmpty(){
        String type = this.type.toLowerCase();
        return StringUtils.hasText(this.value) || Objects.equals(type,"boolean") || Objects.equals(operator,10) || Objects.equals(operator,11);
    }
    /***
     * 将searchData 拼装成criteria的数组  用于组合查询语句
     * 注: 方法没有判断value是否为合规 请在传参之前过滤
     **/
    public static Criteria[] assembleCriteria(List<SearchData> searchData) throws NumberFormatException{
        Criteria[] result = new Criteria[searchData.size()];
        for (int i = 0; i < searchData.size(); i++) {
            if(searchData.get(i).getCollName().contains("caseTags") && searchData.get(i).getValue().contains("垃圾")){
                result[i] = generateTrashEntity(searchData.get(i)).assembleOne();
            } else {
                result[i] = searchData.get(i).assembleOne();
            }
        }
        return result;
    }

    /**
     * 生成匹配标签为垃圾的实体类
     * @param searchData
     * @return
     */
    private static SearchData generateTrashEntity(SearchData searchData){
        SearchData trashSearchData = new SearchData();
        trashSearchData.setCollName("isTrash");
        trashSearchData.setOperator(searchData.operator);
        trashSearchData.setValue("true");
        trashSearchData.setType("boolean");
        return trashSearchData;
    }

    private Criteria assembleOne() throws NumberFormatException {
        Criteria result = Criteria.where(getCollName());
        String type = getType().toLowerCase();
        Object value;
        String trimValue = getValue().trim();
        switch (type) {
            case "int":
                value = Integer.valueOf(trimValue);
                break;
            case "long":
                value = Long.valueOf(trimValue);
                break;
            case "double":
                value = Double.valueOf(trimValue);
                break;
            case "boolean":
                value = Boolean.valueOf(trimValue);
                break;
            case "datetime":
            case "date":
                value =  LocalDateTime.parse(trimValue, DATE_TIME_FORMATTER).atZone(ZoneId.systemDefault()).
                        toInstant().toEpochMilli();
                break;
            case "dateInstant":
                value =  LocalDateTime.parse(trimValue, DATE_TIME_FORMATTER).atZone(ZoneId.systemDefault()).
                        toInstant();
                break;
            default:
                value = trimValue;
                break;
        }
        Integer operator = getOperator();
        if(operator == null){
            throw new RuntimeException("运算符选择错误");
        }
        switch (operator) {
            case 1:
                result.is(value);
                break;
            case 2:
                result.ne(value);
                break;
            case 3:
                result.gt(value);
                break;
            case 4:
                result.gte(value);
                break;
            case 5:
                result.lt(value);
                break;
            case 6:
                result.lte(value);
                break;
            case 7:
                result.regex(ParamUtils.replaceAllMateChar(value.toString()));
                break;
            case 8:
                result.is(true);
                break;
            case 9:
                result.is(false);
                break;
            case 10:
                result.exists(true);
                break;
            case 11:
                result.exists(false);
                break;
            case 12:
                result.not().regex(ParamUtils.replaceAllMateChar(value.toString()));
            default:
                break;
        }
        return result;
    }



}
