package com.zhonghaiwenda.gaea.framework.core.aware;

import org.springframework.beans.factory.Aware;

/**
 * 此类的子类可以感知是否是资产
 * 继承自Spring factory 中的Aware感知接口，表示此类子类也是具有感知功能。
 * 但不具有Spring Aware原本的生命周期回调方法  注意区别
 *
 * @author gxz gongxuanzhang@foxmail.com
 **/
public interface AssetAware extends Aware {


    /**
     *
     * 如果是资产 将返回true
     * @return 是否是资产
     **/
    boolean isAsset();


    /**
     * 如果{@link this#isAsset()} ()} 为true 应该返回asset对应的id
     * 如果是资产  将返回资产Id
     * 如果不是资产 返回null
     * @return  id or null
     **/
    String getAssetId();


}
