package ai.zhuanzhi.webcollector.JoS;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import ai.zhuanzhi.mongodb.MongoDBUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoSBackIssueBrowsingCollector extends BreadthCrawler {
    public JoSBackIssueBrowsingCollector(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        this.addSeed("http://jos.org.cn/jos/issue/browser");
    }

    //    @Override
    public void visit(Page page, CrawlDatums next) {
        String url = page.url();
        String scripts = page.select("script").toString();
        String pattern = "\\[\\{\\\\.*]}]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(scripts);

        String browserJson = null;

        if (m.find()) {
            //save to DB
            browserJson = m.group(0).replace("\\", "");
            List<DBObject> parse = (List<DBObject>) JSON.parse(browserJson);
            MongoDBUtil mongoDB = new MongoDBUtil("172.17.0.2", 27017, "zxw", "admin", "zxw@12345");
            MongoClient mongoClient = mongoDB.getConnection();
            MongoDatabase database = mongoClient.getDatabase("crawl_jos");
            MongoCollection<DBObject> collection = database.getCollection("jos_backIssue", DBObject.class);

            collection.insertMany(parse);
            mongoClient.close();
        }
    }

    public static void main(String[] args) throws Exception {
        JoSBackIssueBrowsingCollector joSBackIssueBrowsingCollector = new JoSBackIssueBrowsingCollector("crawl", true);
        joSBackIssueBrowsingCollector.start(1);
    }
}

