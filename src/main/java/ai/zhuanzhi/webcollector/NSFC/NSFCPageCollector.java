package ai.zhuanzhi.webcollector.NSFC;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.MD5Utils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ai.zhuanzhi.mongodb.MongoDBUtil;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NSFCPageCollector extends BreadthCrawler {
    protected MongoDBUtil mongoDB;
    protected MongoDatabase databaseIn, databaseOut;
    protected MongoClient mongoClient;
    protected Long timeStamp;

    public NSFCPageCollector(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
//        final String username = "H960K1987AJB64HD";
//        final String password = "3D64768712FE8C7D";
//        setRequester(new AbuyunDynamicProxyRequester(
//                username, password
//        ));
        mongoDB = new MongoDBUtil("172.17.0.2", 27017, "zxw", "admin", "zxw@12345");
        mongoClient = mongoDB.getConnection();
        databaseIn = mongoClient.getDatabase("crawl_jos");
        databaseOut = mongoClient.getDatabase("crawl_fund");
        this.setThreads(10);
    }

    public void getFundNoUrls() {
        MongoCollection<Document> collection = databaseIn.getCollection("jos_funds");
        for (Document document : collection.find()) {
            String fundNo = document.get("fundNo", String.class);
            this.addSeedAndReturn("https://www.medsci.cn/sci/nsfc.do?page=1&project_classname_list=&txtitle=" + fundNo + "&sort_type=3").type("seed");
        }
    }

    public void visit(Page page, CrawlDatums next) {
        if (page.matchType("seed")) {
            String href = page.select("strong.m-b-10 a").attr("href");
            if (href != null) {
                next.addAndReturn(href).type("NSFCPage");
            }

        } else if (page.matchType("NSFCPage")) {
            String md5TimeStamp = null;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                timeStamp = System.currentTimeMillis();
                md5TimeStamp = MD5Utils.md5(timeStamp.toString().getBytes());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
            String url = page.url();
            String key = "nsfc_".concat(url);
            String type = "nsfc";
            String html = page.html();
            assert md5TimeStamp != null;
            String crawlId = "nsfc_".concat(md5TimeStamp);
            Long fetchTime = timeStamp;
            Date fetchDate = new Date(Long.parseLong(String.valueOf(timeStamp)));

            Document document = new Document("key", key).append("url", url).append("type", type).append("html", html).append("crawlId", crawlId)
                    .append("fetchTime", fetchTime).append("fetchData", fetchDate);

            databaseOut.getCollection("fund_nsfcPage").insertOne(document);

            org.jsoup.nodes.Document doc = Jsoup.parse(html);
            Elements elements = doc.select("span.font-black");
            for(Element element: elements.subList(10, elements.size())){
                String href = element.select("a").attr("href");
                if(href != null){
                    next.addAndReturn("https://www.medsci.cn" + href).type("NSFCPage");
                }
            }
            System.out.println(url);
        }
    }

    public static void main(String[] args) throws Exception {
        NSFCPageCollector nsfc = new NSFCPageCollector("NSFCrawl", true);
        nsfc.getFundNoUrls();
        nsfc.start(4);
    }
}
