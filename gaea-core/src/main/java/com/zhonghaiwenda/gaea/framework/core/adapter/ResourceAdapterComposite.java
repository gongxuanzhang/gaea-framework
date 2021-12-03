package com.zhonghaiwenda.gaea.framework.core.adapter;

import com.zhonghaiwenda.gaea.framework.core.ResourceAdapter;
import com.zhonghaiwenda.gaea.framework.core.exception.GaeaAdaptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zhonghaiwenda.gaea.framework.core.adapter.GaeaResourceAdapt.QUALIFIER_NAME;

/**
 * @author gxz gongxuanzhang@foxmail.com
 **/
@Component
public class ResourceAdapterComposite implements ResourceAdapter<Object, Object> {

    ThreadLocal<ResourceAdapter> threadLocal = new ThreadLocal<>();

    @Autowired
    @Qualifier(QUALIFIER_NAME)
    private List<ResourceAdapter<?, ?>> resourceAdapterList;

    @Override
    public Object adapt(Object resource) {
        try {
            ResourceAdapter resourceAdapter = threadLocal.get();
            Object adapt = resourceAdapter.adapt(resource);
            return adapt;
        } catch (Exception e) {
            throw new GaeaAdaptException(e.getMessage());
        } finally {
            threadLocal.remove();
        }

    }

    @Override
    public boolean support(Object resource) {
        for (ResourceAdapter<?, ?> resourceAdapter : resourceAdapterList) {
            if (resourceAdapter.support(resource)) {
                threadLocal.set(resourceAdapter);
                return true;
            }
        }
        return false;
    }
}
