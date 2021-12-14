package com.zhonghaiwenda.gaea.framework.core.environment;

import com.zhonghaiwenda.gaea.framework.core.tool.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 加载Gaea环境 根据部署环境自动加载GaeaEnvironment相关属性
 * 此配置类加载时机在SpringCloud配置中心读取之后。
 * 也就是此配置的Environment会根据配置中心内容所影响
 * 如果没有填写Home地址，会自动使用当前目录的父目录为默认home地址
 * @author gxz
 * @date 2021/1/14 0:47
 */


@Slf4j
public class GaeaPropertyFilePathConfiguration implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String nodeHome = environment.getProperty("node.home");
        String nodeName = environment.getProperty("node.name");
        String srcPath = environment.getProperty("node.src-path");
        String appName = environment.getProperty("spring.application.name");
        if (nodeHome == null) {
            //  当组件依赖Spring Cloud时 此声明周期会进入两次，但是不影响上下文的覆盖情况
            //  如果没有设置nodeHome  那么当前目录的父目录会成为默认Home
            nodeHome = System.getProperty("user.dir");
        }
        Map<String, Object> nodeMap = new HashMap<>();
        nodeMap.put("gaea.node-name", nodeName);
        nodeMap.put("gaea.application-name", appName);
        try {
            String layer = appName.split("_")[0];
            String category = appName.split("_")[1];
            nodeMap.put("gaea.category", category);
            nodeMap.put("gaea.layer", layer);
        } catch (Exception e) {
            throw new RuntimeException("加载applicationName失败  请检查[spring.application.name]配置,需要使用[层_模块名]");
        }

        Map<String, Object> fileMap = new HashMap<>(16);
//        fileMap.put("gaea.src-path", srcPath);
//        Assert.notNull(srcPath, "srcPath不能为空");
        fileMap.put("gaea.home-path", nodeHome);


        // home下
        fileMap.put("gaea.config-path", nodeHome + File.separator + "config" + File.separator);
        fileMap.put("gaea.log-path", nodeHome + File.separator + "log" + File.separator);
        String dataPath = nodeHome + File.separator + "data" + File.separator;
        fileMap.put("gaea.data-path", dataPath);
        // data下
        String dataWarehouse = dataPath + File.separator + "dataWarehouse" + File.separator;
        fileMap.put("gaea.data-warehouse-path", dataWarehouse);
        String cache = dataPath + File.separator + "cache" + File.separator;
        fileMap.put("gaea.cache-path", cache);
        fileMap.put("gaea.bak-path", dataPath + File.separator + "bak" + File.separator);
        // dataWarehouse下
        fileMap.put("gaea.data-warehouse-csv-path", dataWarehouse + File.separator + "csv" + File.separator);
        String jsonPath = dataWarehouse + File.separator + "json" + File.separator;
        fileMap.put("gaea.data-warehouse-json-path", jsonPath);
        fileMap.put("gaea.data-warehouse-custom-path", dataWarehouse + File.separator + "custom" + File.separator);
        // json下
        fileMap.put("gaea.cer-chain-path", jsonPath + File.separator + "cerChain" + File.separator);
        fileMap.put("gaea.session-adjust-path", jsonPath + File.separator + "sessionadjust" + File.separator);
        fileMap.put("gaea.alarm-material-path", jsonPath + File.separator + "alarmMaterial" + File.separator);
        // cache下
        fileMap.put("gaea.event-data-path", cache + File.separator + "eventData" + File.separator);
        BiConsumer<File, Boolean> biConsumer = (file, createSuccess) -> {
            if (createSuccess) {
                log.info("{}创建成功", file.getAbsolutePath());
            } else {
                log.info("{},创建失败，可能已经存在", file.getAbsolutePath());
            }
        };
        FileUtils.buildDirs(biConsumer, fileMap.values().stream().map(Object::toString).toArray(String[]::new));
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addLast(new GaeaProperty("gaeaNode", nodeMap));
        propertySources.addLast(new GaeaProperty("gaeaFile", fileMap));

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 11;
    }

    public static class GaeaProperty extends MapPropertySource {

        public GaeaProperty(String name, Map<String, Object> source) {
            super(name, source);
        }
    }
}
