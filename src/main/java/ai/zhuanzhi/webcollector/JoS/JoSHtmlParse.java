package ai.zhuanzhi.webcollector.JoS;

import ai.zhuanzhi.contentextractor.Article;
import ai.zhuanzhi.contentextractor.Author;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoSHtmlParse {


    public static org.bson.Document parse(String html) throws ParseException {

        if (html == null)
            return null;
        Document doc = Jsoup.parse(html);
        /*
            get from html
        */
        //Title
        String zhArticleTitle = doc.select("div.zh div.title").text();
        String enArticleTitle = doc.select("div.en[id='EnTitle'] div.title[id='EnTitleValue']").text();
        //DOI
        String DOI = doc.select("a[id='DOIValue']").text();
        //DOIUrl
        String DOIUrl = doc.select("a[id='DOIValue']").attr("href");
        //PdfUrl
        String pdfUrl = doc.select("a[id='PdfUrl']").first().attr("href");
        //PaperUrl
        String paperUrl = pdfUrl.replace("pdf", "abstract");
        //taskId
        String key = "jos_" + paperUrl;
        //FundProject
        String fundProject = doc.select("p[id='CnFundProjectValue']").text();
        //zhAbstract
        String zhAbstract = doc.select("p[id='CnAbstractValue']").text();
        //enAbstract
        String enAbstract = doc.select("p[id='EnAbstractValue']").text();
        //copy cite
        String cite = doc.select("p[id='cp-cont']").text();
        //send time
        Date sendTime = null, lastModifyTime = null, adoptTime = null, publishTime = null, bookPublishedTime = null;
        String sendTimeS = doc.select("span[id='SendTimeValue']").text();
        if (!sendTimeS.equals("")) {
            sendTime = new SimpleDateFormat("yyyy-mm-dd").parse(sendTimeS);
        }
        //LastModify time
        String lastModifyTimeS = doc.select("span[id='LastModifyTimeValue']").text();
        if (!lastModifyTimeS.equals("")) {
            lastModifyTime = new SimpleDateFormat("yyyy-mm-dd").parse(lastModifyTimeS);
        }
        //Adopt time
        String adoptTimeS = doc.select("span[id='AdoptTimeValue']").text();
        if (!adoptTimeS.equals("")) {
            adoptTime = new SimpleDateFormat("yyyy-mm-dd").parse(adoptTimeS);
        }
        //Publish time  在线发布日期
        String publishTimeS = doc.select("span[id='PublishTimeValue']").text();
        if (!publishTimeS.equals("")) {
            publishTime = new SimpleDateFormat("yyyy-mm-dd").parse(publishTimeS);
        }
        //BookPublishedTime  出版日期
        String bookPublishedTimeS = doc.select("span[id='BookPublishedTimeValue']").text();
        if (!bookPublishedTimeS.equals("")) {
            bookPublishedTime = new SimpleDateFormat("yyyy-mm-dd").parse(bookPublishedTimeS);
        }
        Long parseTimeStamp = System.currentTimeMillis();
        /*
            get from script
        */
        String scripts = doc.select("script").toString();
        Pattern r;
        Matcher m;
        //authors information
        String authorInfoPattern = "authors\\\\\":(.*?)}\";";
        r = Pattern.compile(authorInfoPattern);
        m = r.matcher(scripts);
        Author[] authors = null;
        Gson gson = new Gson();
        if (m.find()) {
            String authorsString = m.group(1).replace("\\", "")
                    .replace("cn_name", "zhName")
                    .replace("en_name", "enName")
                    .replace("cn_institution", "znInstitution")
                    .replace("en_institution", "enInstitution")
                    .replace("is_first_author", "isFirstAuthor")
                    .replace("is_contact_author", "isContactAuthor")
                    .replace("first_name", "firstName")
                    .replace("middle_name", "middleName")
                    .replace("last_name", "lastName")
                    .replace("institute_superscript", "instituteSuperscript");
            authors = gson.fromJson(authorsString, Author[].class);
        }
        //keywords(zh/en)
        List<String> zhKeywords = null, enKeywords = null;
        String zhPattern = "var strCnKeyWord=\"(.*?)\";";
        String enPattern = "var strEnKeyWord=\"(.*?)\";";
        r = Pattern.compile(zhPattern);
        m = r.matcher(scripts);
        if (m.find()) {
            zhKeywords = Arrays.asList(m.group(1).split(";"));
        }
        r = Pattern.compile(enPattern);
        m = r.matcher(scripts);
        if (m.find()) {
            enKeywords = Arrays.asList(m.group(1).split(";"));
        }
        Article article = new Article(authors, zhArticleTitle, enArticleTitle, DOI, DOIUrl, pdfUrl, paperUrl,
                key, fundProject, zhAbstract, enAbstract, cite, sendTime, lastModifyTime, adoptTime, publishTime,
                bookPublishedTime, parseTimeStamp, zhKeywords, enKeywords);
//        System.out.println(Thread.currentThread().toString() + "->" + article);
        return org.bson.Document.parse(gson.toJson(article));

    }
}
