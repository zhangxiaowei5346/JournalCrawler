package ai.zhuanzhi.contentparser.jos

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import ai.zhuanzhi.tool.model.{NamedLink, Topic}
import com.google.gson.Gson
import com.mongodb.spark.MongoSpark
import com.zhuanzhi.utils.KeywordStrParse
import org.apache.ivy.osgi.util.ZipUtil.zip
import org.apache.spark.sql.SparkSession
import org.bson.Document

import java.net.URLEncoder
import java.util
import java.util.Date

object KeywordsParse {

  def main(args: Array[String]): Unit = {

    val spark = new SparkMongoDBUtil(inputDatabase = "crawl_jos", outputDatabase = "crawl_jos",
      inputCollection = "jos_parsedArticles", outputCollection = "jos_topics", appName="keywordParse")
      .getSparkSession

    spark.sparkContext.setLogLevel("ERROR")

    val pipeline1 = "{'$project': {'_id' : 0, 'zhKeywords': 1}}"
    val pipeline2 = "{'$unwind': '$zhKeywords'}"


    val keywords = MongoSpark.load(spark.sparkContext).withPipeline(Seq(Document.parse(pipeline1), Document.parse(pipeline2)))
      .filter(keywordInfo => keywordInfo != null && keywordInfo.getString("zhKeywords") != null && !keywordInfo.getString("zhKeywords").equals(""))
      .groupBy(keywordInfo => keywordInfo.getString("zhKeywords"))
      .map(keywordInfo => {
        val keyword = KeywordStrParse.keywordParse(keywordInfo._1)
        val keywordDoc = new Document()
        if (keyword != null) {
          keywordDoc.append("keyword", keyword)
          keywordDoc
        }
        else {
          null
        }
      })
      .filter(keywordInfo => keywordInfo != null)
      .groupBy(keywordInfo => keywordInfo.getString("keyword"))
//      .map(keywordInfo => try {
//        val topic = new Topic(
//          keywordInfo._1.replace(" ", ""),
//          new Date().getTime,
//          new Date().getTime,
//          new util.ArrayList[String](),
//          new util.ArrayList[String](),
//          keywordInfo._1,
//          "",
//          false,
//          false,
//          false,
//          false,
//          new util.ArrayList[String](),
//          new util.ArrayList[String](),
//          new util.ArrayList[String](),
//          new util.ArrayList[NamedLink](),
//          new util.ArrayList[Topic#RelatedTopics]())
//        topic.toDoc
//      } catch {
//        case exception: Exception =>
//          println(exception)
//          null
//      })
//      .filter(keywordInfo => keywordInfo != null)

    //    save to mongo
    //    MongoSpark.save(keywords)

  }
}
