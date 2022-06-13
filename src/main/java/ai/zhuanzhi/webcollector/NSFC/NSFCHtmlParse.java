package ai.zhuanzhi.webcollector.NSFC;

import ai.zhuanzhi.contentextractor.NSFCFund;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.*;

public class NSFCHtmlParse {
    public static org.bson.Document parse(String html) throws ParseException {
        if (html == null)
            return null;
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("span.font-black");
        String fundName = elements.get(0).text();
        String fundNo = elements.get(1).text();
        String fundSubject = elements.get(2).text();
        String fundType = elements.get(3).text();
        String fundInCharge = elements.get(4).text();
        String fundSupportingUnit = elements.get(5).text();
        String fundApprovedYear = elements.get(6).text();
        String fundStartEndTime = elements.get(7).text();
        String fundApprovedAmount = elements.get(8).text();
        String fundAbstract = elements.get(9).text();
        //error: need transform map to Document
        List<org.bson.Document> fundRelated = new ArrayList<org.bson.Document>();
        for (Element element : elements.subList(10, elements.size())) {
            org.bson.Document document = new org.bson.Document();
            document.append("fundRelated", element.text())
                    .append("fundRelatedUrl", "https://www.medsci.cn" + element.select("a").attr("href"));
            fundRelated.add(document);
        }
        NSFCFund nsfcFund = new NSFCFund(fundName, fundNo, fundSubject, fundType,
                fundInCharge, fundSupportingUnit, fundApprovedYear, fundStartEndTime,
                fundApprovedAmount, fundAbstract, fundRelated);
        Gson gson = new Gson();
        return org.bson.Document.parse(gson.toJson(nsfcFund));
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(NSFCHtmlParse.parse(""));
    }
}
