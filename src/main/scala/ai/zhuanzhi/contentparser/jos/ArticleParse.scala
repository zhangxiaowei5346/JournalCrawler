package ai.zhuanzhi.contentparser.jos

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import com.mongodb.spark.{MongoSpark, toDocumentRDDFunctions}
import org.apache.spark.sql.SparkSession
import com.zhuanzhi.webcollector.JoS.JoSHtmlParse

import java.net.URLEncoder

object ArticleParse {

  def main(args: Array[String]): Unit = {

    val spark = new SparkMongoDBUtil(inputDatabase = "crawl_jos", outputDatabase = "crawl_jos",
      inputCollection = "jos_articles", outputCollection = "jos_parsedArticles", appName="articleParse")
      .getSparkSession

    val sc = spark.sparkContext
    val articles = MongoSpark.load(sc)
    val articlesHtml = articles
      .map(doc => (doc.getString("html"), doc.getString("key")))

    val parsed_articles = articlesHtml
      .map(articleHtml => JoSHtmlParse.parse(articleHtml._1))

//    save to mongodb
//    parsed_articles.saveToMongoDB()
  }
}
