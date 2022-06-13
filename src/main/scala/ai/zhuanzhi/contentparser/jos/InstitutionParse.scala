package ai.zhuanzhi.contentparser.jos

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import ai.zhuanzhi.tool.model.{NamedLink, Topic}
import ai.zhuanzhi.utils.InstitutionStrParse
import com.google.gson.Gson
import com.mongodb.spark.{MongoSpark, toDocumentRDDFunctions}
import com.zhuanzhi.utils.InstitutionStrParse
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.bson.Document

import java.net.URLEncoder
import java.util
import java.util.Date
import scala.+:

object InstitutionParse {

  def main(args: Array[String]): Unit = {

    val spark = new SparkMongoDBUtil(inputDatabase = "crawl_jos", outputDatabase = "crawl_jos",
      inputCollection = "jos_parsedArticles", outputCollection = "jos_topics", appName="institutionParse")
      .getSparkSession

    spark.sparkContext.setLogLevel("ERROR")

    val pipeline1 = "{'$project': {'_id' : 0}}"
    val pipeline2 = "{'$unwind': '$authors'}"
    val pipeline3 = "{'$project': {znInstitution: '$authors.znInstitution'}}"

    val institutionInfo = MongoSpark.load(spark.sparkContext).withPipeline(Seq(Document.parse(pipeline1), Document.parse(pipeline2), Document.parse(pipeline3)))
      .filter(institutionInfo => institutionInfo.getString("znInstitution") != null && !institutionInfo.getString("znInstitution").equals(""))
      .groupBy(institutionInfo => institutionInfo.getString("znInstitution"))
      .map(institutionInfo => {
        val institution = InstitutionStrParse.institutionParse(institutionInfo._1)
        val document = new Document()
        if (institution != null) {
          document.append("university", institution.get(0)).append("college", institution.get(1))
        }
        document
      })
      .filter(institutionInfo => institutionInfo != null)
      .map(institutionInfo => ((institutionInfo.getString("university"), institutionInfo.getString("college")), ""))
      .groupByKey()
      .map(institutionInfo => institutionInfo._1)
      .groupByKey()
//      .map(institutionInfo => try {
//        val topic = new Topic(
//          institutionInfo._1,
//          new Date().getTime,
//          new Date().getTime,
//          new util.ArrayList[String](),
//          new util.ArrayList[String](),
//          institutionInfo._1 + ", " + institutionInfo._2.mkString(", "),
//          "",
//          false,
//          false,
//          false,
//          false,
//          new util.ArrayList[String](),
//          new util.ArrayList[String](),
//          new util.ArrayList[String](),
//          new util.ArrayList[NamedLink](),
//          new util.ArrayList[Topic.RelatedTopic]())
//        topic.toDoc
//      } catch {
//        case exception: Exception =>
//          println(exception)
//          null
//      })
//      .filter(institutionInfo => institutionInfo != null)

    //    save to mongo
//    MongoSpark.save(institutionInfo)
  }
}
