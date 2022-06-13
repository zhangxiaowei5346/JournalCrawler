package ai.zhuanzhi.contentextractor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Article {
    private Author[] authors;
    private String zhArticleTitle, enArticleTitle, DOI, DOIUrl,
            pdfUrl, paperUrl, key, fundProject, zhAbstract, enAbstract,
            cite;
    private Date sendTime, lastModifyTime, adoptTime, publishTime,
            bookPublishedTime;
    private Long parseTimeStamp;
    private List<String> zhKeywords;
    private List<String> enKeywords;

    @Override
    public String toString() {
        return "Article{" +
                "authors=" + Arrays.toString(authors) +
                ", zhArticleTitle='" + zhArticleTitle + '\'' +
                ", enArticleTitle='" + enArticleTitle + '\'' +
                ", DOI='" + DOI + '\'' +
                ", DOIUrl='" + DOIUrl + '\'' +
                ", pdfUrl='" + pdfUrl + '\'' +
                ", paperUrl='" + paperUrl + '\'' +
                ", key='" + key + '\'' +
                ", fundProject='" + fundProject + '\'' +
                ", zhAbstract='" + zhAbstract + '\'' +
                ", enAbstract='" + enAbstract + '\'' +
                ", cite='" + cite + '\'' +
                ", sendTime=" + sendTime +
                ", lastModifyTime=" + lastModifyTime +
                ", adoptTime=" + adoptTime +
                ", publishTime=" + publishTime +
                ", bookPublishedTime=" + bookPublishedTime +
                ", parseTimeStamp=" + parseTimeStamp +
                ", zhKeywords=" + zhKeywords +
                ", enKeywords=" + enKeywords +
                '}';
    }

    public Author[] getAuthors() {
        return authors;
    }

    public void setAuthors(Author[] authors) {
        this.authors = authors;
    }

    public String getZhArticleTitle() {
        return zhArticleTitle;
    }

    public void setZhArticleTitle(String zhArticleTitle) {
        this.zhArticleTitle = zhArticleTitle;
    }

    public String getEnArticleTitle() {
        return enArticleTitle;
    }

    public void setEnArticleTitle(String enArticleTitle) {
        this.enArticleTitle = enArticleTitle;
    }

    public String getDOI() {
        return DOI;
    }

    public void setDOI(String DOI) {
        this.DOI = DOI;
    }

    public String getDOIUrl() {
        return DOIUrl;
    }

    public void setDOIUrl(String DOIUrl) {
        this.DOIUrl = DOIUrl;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPaperUrl() {
        return paperUrl;
    }

    public void setPaperUrl(String paperUrl) {
        this.paperUrl = paperUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFundProject() {
        return fundProject;
    }

    public void setFundProject(String fundProject) {
        this.fundProject = fundProject;
    }

    public String getZhAbstract() {
        return zhAbstract;
    }

    public void setZhAbstract(String zhAbstract) {
        this.zhAbstract = zhAbstract;
    }

    public String getEnAbstract() {
        return enAbstract;
    }

    public void setEnAbstract(String enAbstract) {
        this.enAbstract = enAbstract;
    }

    public String getCite() {
        return cite;
    }

    public void setCite(String cite) {
        this.cite = cite;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Date getAdoptTime() {
        return adoptTime;
    }

    public void setAdoptTime(Date adoptTime) {
        this.adoptTime = adoptTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getBookPublishedTime() {
        return bookPublishedTime;
    }

    public void setBookPublishedTime(Date bookPublishedTime) {
        this.bookPublishedTime = bookPublishedTime;
    }

    public Long getParseTimeStamp() {
        return parseTimeStamp;
    }

    public void setParseTimeStamp(Long parseTimeStamp) {
        this.parseTimeStamp = parseTimeStamp;
    }

    public List<String> getZhKeywords() {
        return zhKeywords;
    }

    public void setZhKeywords(List<String> zhKeywords) {
        this.zhKeywords = zhKeywords;
    }

    public List<String> getEnKeywords() {
        return enKeywords;
    }

    public void setEnKeywords(List<String> enKeywords) {
        this.enKeywords = enKeywords;
    }

    public Article(Author[] authors, String zhArticleTitle, String enArticleTitle, String DOI, String DOIUrl,
                   String pdfUrl, String paperUrl, String key, String fundProject, String zhAbstract,
                   String enAbstract, String cite, Date sendTime, Date lastModifyTime, Date adoptTime,
                   Date publishTime, Date bookPublishedTime, Long parseTimeStamp, List<String> zhKeywords, List<String> enKeywords) {
        this.authors = authors;
        this.zhArticleTitle = zhArticleTitle;
        this.enArticleTitle = enArticleTitle;
        this.DOI = DOI;
        this.DOIUrl = DOIUrl;
        this.pdfUrl = pdfUrl;
        this.paperUrl = paperUrl;
        this.key = key;
        this.fundProject = fundProject;
        this.zhAbstract = zhAbstract;
        this.enAbstract = enAbstract;
        this.cite = cite;
        this.sendTime = sendTime;
        this.lastModifyTime = lastModifyTime;
        this.adoptTime = adoptTime;
        this.publishTime = publishTime;
        this.bookPublishedTime = bookPublishedTime;
        this.parseTimeStamp = parseTimeStamp;
        this.zhKeywords = zhKeywords;
        this.enKeywords = enKeywords;
    }
}
