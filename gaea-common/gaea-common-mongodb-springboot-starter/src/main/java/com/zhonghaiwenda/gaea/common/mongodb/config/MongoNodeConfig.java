package com.zhonghaiwenda.gaea.common.mongodb.config;

import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ClusterSettings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gongxuanzhang
 */
@Setter
@Getter
public class MongoNodeConfig {

    /**
     * 主机名 可填写localhost
     **/
    protected List<String> host;
    /**
     * 端口
     **/
    protected int port = 27017;
    protected String user;
    protected String password;
    protected String database;
    /**
     * 是否忽略Spring提供的_class字段
     **/
    protected boolean ignoreClassAttr = true;
    final private Block<ClusterSettings.Builder> block = settings -> settings.hosts(getServerAddress());

    public List<ServerAddress> getServerAddress() {
        List<ServerAddress> address = new ArrayList<>();
        for (String hostAddress : host) {
            address.add(new ServerAddress(hostAddress, port));
        }
        return address;
    }

    public MongoCredential getMongoCredential() {
        return MongoCredential.createCredential(user, database, password.toCharArray());
    }


    public MongoDatabaseFactory toDbFactory() {
        MongoClientSettings set = MongoClientSettings.builder()
                .credential(getMongoCredential()).applyToClusterSettings(getBlock()).build();
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(set), this.database);
    }
}
