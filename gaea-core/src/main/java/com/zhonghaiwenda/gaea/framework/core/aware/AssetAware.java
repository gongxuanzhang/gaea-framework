package com.zhonghaiwenda.gaea.framework.core.aware;

/**
 * 此类的子类可以感知是否是资产
 *
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
     * 如果{@link #isAsset}  为true 应该返回asset对应的id
     * 如果是资产  将返回资产Id
     * 如果不是资产 返回null
     * @return  id or null
     **/
    String getAssetId();


}
