package ai.zhuanzhi.webcollector.JoS;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.MD5Utils;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import ai.zhuanzhi.mongodb.MongoDBUtil;
import org.bson.Document;
import org.jsoup.nodes.Element;
import ai.zhuanzhi.plugins.AbuyunDynamicProxyRequester;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JoSSpecialIssueArticleCollector extends BreadthCrawler {
    protected MongoDBUtil mongoDB;
    protected MongoDatabase database;
    protected MongoClient mongoClient;
    protected List<Document> specialIssueArticleDocuments, articleDocuments;
    protected Long timeStamp;

    public JoSSpecialIssueArticleCollector(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
        final String username = "H960K1987AJB64HD";
        final String password = "3D64768712FE8C7D";
        setRequester(new AbuyunDynamicProxyRequester(
                username, password
        ));
        /*
         * MongoDB connnection
         * */
        mongoDB = new MongoDBUtil("172.17.0.2", 27017, "zxw", "admin", "zxw@12345");
        mongoClient = mongoDB.getConnection();
        database = mongoClient.getDatabase("crawl_jos");
        specialIssueArticleDocuments = new ArrayList<Document>();
        articleDocuments = new ArrayList<Document>();
        this.setThreads(2);

    }


    public List<String> getBackIssueBrowsingUrls() {

        MongoCollection<Document> collection = database.getCollection("jos_backIssue");
        List<String> urls = new ArrayList<String>();
        for (Document document : collection.find()) {
            List<Document> issues = document.get("issues", List.class);
            for (Document issue : issues) {
                String issueUrl = issue.get("issue_url", String.class);
                urls.add("http://jos.org.cn/" + issueUrl);
                this.addSeedAndReturn("http://jos.org.cn/" + issueUrl).type("seed");
            }
        }
        return urls;
    }


//    @Override
    public void visit(Page page, CrawlDatums next) {

        if (page.matchType("seed")) {
            for (Element element : page.select("div.article_title a")) {
                String url = element.attr("href");
                next.addAndReturn("http://jos.org.cn/" + url).type("article");
                Document document = new Document("fromUrl", page.url()).append("url", "http://jos.org.cn/" + url)
                        .append("type", "specialIssue");
                specialIssueArticleDocuments.add(document);
                System.out.println(url);
            }

        } else if (page.matchType("article")) {

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
            String key = "jos_".concat(url);
            String type = "article";
            String html = page.html();
            String crawlId = "jos_".concat(md5TimeStamp);
            Long fetchTime = timeStamp;
            Date fetchDate = new Date(Long.parseLong(String.valueOf(timeStamp)));

            Document document = new Document("key", key).append("url", url).append("type", type).append("html", html).append("crawlId", crawlId)
                    .append("fetchTime", fetchTime).append("fetchData", fetchDate);
            articleDocuments.add(document);
            System.out.println(url);
        }
    }

    public static void main(String[] args) throws Exception {

        JoSSpecialIssueArticleCollector joSSpecialIssueArticleCollector = new JoSSpecialIssueArticleCollector("crawl", true);
        joSSpecialIssueArticleCollector.getBackIssueBrowsingUrls();
        System.out.println("end get back issue browsing urls");

        joSSpecialIssueArticleCollector.start(4);
        System.out.println("end crawling!");

        MongoCollection<Document> collection = joSSpecialIssueArticleCollector.database.getCollection("jos_specialIssue");
        collection.insertMany(joSSpecialIssueArticleCollector.specialIssueArticleDocuments);

        collection = joSSpecialIssueArticleCollector.database.getCollection("jos_articles");
        collection.insertMany(joSSpecialIssueArticleCollector.articleDocuments);

        joSSpecialIssueArticleCollector.mongoClient.close();
    }
}
