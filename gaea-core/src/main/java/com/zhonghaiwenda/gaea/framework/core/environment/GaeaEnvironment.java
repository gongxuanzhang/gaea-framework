package com.zhonghaiwenda.gaea.framework.core.environment;

import com.zhonghaiwenda.gaea.framework.core.tool.FileUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 *
 * HOME
 * ├── config #各模块运行所需配置文件，不用修改
 * │   ├── dgaSource
 * │   ├── geo2ip
 * │   ├── systemRule
 * │   └── trainingResultSet
 * └── log
 * ├── error
 * └── standard
 * <p>
 * DATA    # 数据路径，如果不配置默认写在$HOME/data
 * ├── dataWarehouse   #数据仓库路径
 * │   ├── csv
 * │   │   ├── session
 * │   │   └── more...
 * │   ├── json
 * |   │   ├── alarmMaterial
 * │   │   ├── impSession
 * │   │   └── more...
 * │   └── custom
 * │       ├── analysisPileLog
 * │       └── more...
 * ├── bak # 数据备份路径
 * │   ├── session
 * │   ├── ssl
 * │   └── more...
 * └── cache   # 缓存的json数据，由logstash统一进行入库
 * ├── eventData
 * ├── alarm
 * └── more...
 * <p>
 * SRC    # 探针输出路径，gaea数据输入起点
 * ├── session
 * ├── ssl
 * └── more...
 *
 * 这个类是Properties类 但是不需要配置这么多属性
 * 只需要记录关键的节点。
 * 有一个专门的类会在Nacos读完环境内容之后将这些内容塞进spring环境容器中
 * @see GaeaPropertyFilePathConfiguration
 * @author gxz gongxuanzhang@foxmail.com
 **/

@ConfigurationProperties(prefix = "gaea")
@Data
public class GaeaEnvironment {

    private final static String SEPARATOR = File.separator;


    private GaeaRuntimeProperties runtime;

    private GaeaSrcProperties src = new GaeaSrcProperties();

    private String nodeName;

    private String applicationName;

    private String layer;

    private String category;

    private String srcPath;

    private String homePath;

    // home 目录下

    private String configPath;

    private String dataPath;

    private String logPath;


    // data下

    private String cachePath;

    private String bakPath;

    private String dataWarehousePath;
    // dataWarehouse下

    private String dataWarehouseCsvPath;

    private String dataWarehouseJsonPath;

    private String dataWarehouseCustomPath;

    // json下

    private String cerChainPath;

    private String sessionAdjustPath;

    private String alarmMaterialPath;

    // cache下

    private String eventDataPath;

    public String getCachePath(String category) {
        String path = getCachePath() + SEPARATOR + category + SEPARATOR;
        FileUtils.buildFiles(path);
        return path;
    }

    public String getBakPath(String category) {
        String path = getBakPath() + SEPARATOR + category + SEPARATOR;
        FileUtils.buildFiles(path);
        return path;
    }

    public String getDataWarehouseCsvPath(String category) {
        String path = getDataWarehouseCsvPath() + SEPARATOR + category + SEPARATOR;
        FileUtils.buildFiles(path);
        return path;
    }

    public String getDataWarehouseJsonPath(String category) {
        String path = getDataWarehouseJsonPath() + SEPARATOR + category + SEPARATOR;
        FileUtils.buildFiles(path);
        return path;
    }

    public String getSrcPath(String category) {
        String path = getSrcPath() + SEPARATOR + category + SEPARATOR;
        FileUtils.buildFiles(path);
        return path;
    }


}
