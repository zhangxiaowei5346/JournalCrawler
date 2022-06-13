package ai.zhuanzhi.webcollector.NSTRS;
//

import ai.zhuanzhi.plugins.AbuyunDynamicProxyRequester;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.plugin.net.OkHttpRequester;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import ai.zhuanzhi.mongodb.MongoDBUtil;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.bson.Document;
//


public class NSTRSPageCollector extends BreadthCrawler {

    protected MongoDBUtil mongoDB;
    protected MongoDatabase databaseOut;
    protected MongoClient mongoClient;


    public NSTRSPageCollector(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        this.setThreads(10);
        final String username = "H960K1987AJB64HD";
        final String password = "3D64768712FE8C7D";
        setRequester(new AbuyunDynamicProxyRequester(
                username, password
        ));
        setRequester(new OkHttpRequester() {
            @Override
            public Request.Builder createRequestBuilder(CrawlDatum crawlDatum) {
                Request.Builder requestBuilder = super.createRequestBuilder(crawlDatum);
                RequestBody requestBody;
                requestBody = RequestBody.create(null, new byte[]{});
                return requestBuilder.post(requestBody);
            }
        });

        // department
//        for (int pageNo = 1; pageNo <= 22342; pageNo++) {
//            this.addSeed(String.format("https://www.nstrs.cn/rest/kjbg/wfKjbg/list?" +
//                    "pageNo=%d&fieldCode=&classification=&kjbgRegion=&kjbgType=&grade=1", pageNo));
//        }

        // local
        for (int pageNo = 1; pageNo <= 13472; pageNo++) {
            this.addSeed(String.format("https://www.nstrs.cn/rest/kjbg/wfKjbg/list?" +
                    "pageNo=%d&fieldCode=&classification=&kjbgRegion=&kjbgType=&grade=2", pageNo));
        }
        mongoDB = new MongoDBUtil("172.17.0.2", 27017, "zxw", "admin", "zxw@12345");
        mongoClient = mongoDB.getConnection();
        databaseOut = mongoClient.getDatabase("fund_nstrs");
    }


    @Override
    public void visit(Page page, CrawlDatums next) {
        JsonObject jsonObject = page.jsonObject();
        JsonObject result = jsonObject.getAsJsonObject("RESULT");
        JsonArray list = result.getAsJsonArray("list");
        //department
//        list.forEach(fund -> databaseOut.getCollection("fund_department").insertOne(Document.parse(fund.toString())));
        //local
        list.forEach(fund -> databaseOut.getCollection("fund_local").insertOne(Document.parse(fund.toString())));

    }

    public static void main(String[] args) throws Exception {
        NSTRSPageCollector nstrsPageCollector = new NSTRSPageCollector("NSTRSCrawl", true);
        nstrsPageCollector.start(1);
        nstrsPageCollector.mongoClient.close();
    }
}

