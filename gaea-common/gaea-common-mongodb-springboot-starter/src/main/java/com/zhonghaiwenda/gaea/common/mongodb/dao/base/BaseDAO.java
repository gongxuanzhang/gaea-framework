package com.zhonghaiwenda.gaea.common.mongodb.dao.base;


import com.zhonghaiwenda.gaea.common.mongodb.dao.query.Page;
import com.zhonghaiwenda.gaea.common.mongodb.dao.query.QueryDTO;
import org.springframework.data.mongodb.core.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 核心DAO
 * 这个一个基础DAO接口  所有数据库相关都可通过此接口进行子类实现
 *
 * @param <M>    实体泛型
 * @param <Q>    查询基类 不同框架和不同数据库有各种实现，spring-data-mongodb 就是Query
 * @param <QDTO> 查询实体，主要针对controller接受的页面参数
 * @author gxz
 * @date 2019/12/11
 */
public interface BaseDAO<M, Q, QDTO extends QueryDTO> {

    // 插入相关

    /**
     * 插入, 插入后主键会赋值给model
     *
     * @param model 实体
     **/
    void insert(M model);

    /**
     * 批量插入,插入后主键会赋值给model
     *
     * @param model 插入的集合数据
     **/
    void insert(List<M> model);

    /**
     * 存在就修改，否则就保存
     *
     * @param model 实体
     **/
    void saveOrUpdate(M model);


    /**
     * 插入之前校验是否数据库没有冲突内容 在insert方法中默认调用 子类只需要实现即可
     *
     * @param model 实体
     * @return 是否通过校验
     **/
    boolean insertCheck(M model);


    /**
     * 修改之前校验是否数据库没有冲突内容 在update方法中默认调用 子类只需要实现即可
     *
     * @return 是否通过校验
     * @author gxz
     **/
    boolean updateCheck(M m);

    // 删除相关

    /**
     * 根据ID删除
     *
     * @param id id
     * @return 删除记录数
     **/
    long deleteById(Serializable id);

    /**
     * 删除实体
     *
     * @param query 实体
     * @return 删除记录数
     **/
    long delete(Q query);

    /**
     * 根据id批量删除
     *
     * @param ids ids
     * @return 删除记录数
     **/
    long deleteByIds(List<String> ids);

    // 修改相关

    /**
     * 实体修改
     *
     * @param model 实体
     * @return 修改行数
     */
    long update(M model);

    /**
     * 根据ID修改
     *
     * @param id     id
     * @param update 修改内容
     * @return 返回个啥
     **/
    long update(Serializable id, Update update);

    /**
     * 根据拼装的实体修改
     *
     * @param condition 条件实体
     * @param update    修改条件
     * @return 修改了几条
     **/
    long update(M condition, M update);

    /**
     * 根据id修改
     *
     * @param ids   ids
     * @param model 修改实体
     * @return 修改行数
     **/
    long update(List<String> ids, M model);

    /**
     * 根据id 修改
     *
     * @param ids    ids
     * @param update 修改条件
     * @return 修改多少行数
     **/
    long update(List<String> ids, Update update);

    /**
     * 根据条件修改
     *
     * @param query  查询条件
     * @param update 修改条件
     * @return 修改条数
     **/
    long update(Q query, Update update);

    // 查询相关

    /**
     * 根据id修改
     *
     * @param id id
     * @return 查询内容
     **/
    M findById(Serializable id);

    /**
     * 根据ids查询
     *
     * @param ids ids
     * @return 查询内容
     **/
    List<M> findByIds(Collection<String> ids);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @param skip  skip
     * @param limit limit
     * @return 封装成Page的查询结果
     * @author gxz gongxuanzhang@foxmail.com
     **/
    Page<M> findList(Q query, int skip, int limit);

    /**
     * 分页查询 只有条件  分页信息被封装在了query中
     * 需要子类自行处理
     *
     * @param query query条件
     * @return 封装成Page的查询结果
     **/
    Page<M> findList(Q query);

    /**
     * 分页查询 只有条件  分页信息被封装在了query中
     * 需要子类自行处理
     *
     * @param queryDTO query实体 一般是controller接受的内容
     * @return 封装成Page的查询结果
     **/
    Page<M> findList(QDTO queryDTO);

    /**
     * 根据query查询
     *
     * @param query query
     * @return 查询结果
     **/
    List<M> findListData(Q query);

    /**
     * 根据query查询数量
     *
     * @param query query
     * @return 结果数量
     **/
    long findCount(Q query);


    /**
     * 根据queryDto查询
     *
     * @param queryDTO 查询实体
     * @return 查询结果
     **/
    List<M> findListData(QDTO queryDTO);

    /**
     * 根据queryDto查询数量
     *
     * @param queryDTO 查询实体
     * @return 返回个啥
     **/
    long findCount(QDTO queryDTO);

    /**
     * 分页查询，只返回查询结果
     *
     * @param query query
     * @param skip  skip
     * @param limit limit
     * @return 返回查询内容
     **/
    List<M> findListData(Q query, int skip, int limit);

    /**
     * 查询所有
     *
     * @return 所有内容
     **/
    List<M> findAll();

    /**
     * 根据条件查询某一个
     *
     * @param query query查询内容
     * @return 返回个啥
     **/
    M findOne(Q query);

    /**
     * 根据条件返回查询对象是否存在
     *
     * @param query query
     * @return 是否存在
     **/
    boolean isExists(Q query);


}
