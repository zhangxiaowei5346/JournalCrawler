package ai.zhuanzhi.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

public class MongoDBUtil {
    private String host;
    private Integer port = 27017;
    private String userName, source, password;
    public String database;

    public MongoDBUtil(){}

    public MongoDBUtil(String host, Integer port, String userName, String source, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.source = source;
        this.password = password;
    }

    public MongoClient getConnection() {
        List<ServerAddress> adds = new ArrayList<ServerAddress>();
        //ServerAddress()两个参数分别为 服务器地址 和 端口
        ServerAddress serverAddress = new ServerAddress(host, port);
        adds.add(serverAddress);
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(userName, source, password.toCharArray());
        credentials.add(mongoCredential);
        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(adds, credentials);
        return mongoClient;
    }

    public void insert(MongoClient mongoClient, String database){
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
    }

}
