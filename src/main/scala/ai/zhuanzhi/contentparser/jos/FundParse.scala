package ai.zhuanzhi.contentparser.jos

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import ai.zhuanzhi.tool.model.{NamedLink, Topic}
import ai.zhuanzhi.utils.FundStrParse
import com.mongodb.spark.MongoSpark
import ai.zhuanzhi.utils.FundStrParse

import scala.collection.JavaConverters._
import org.bson.Document

import java.util.Date
import scala.collection.mutable.ListBuffer

object FundParse {

  def main(args: Array[String]): Unit = {

    val spark = new SparkMongoDBUtil(inputDatabase = "crawl_jos", outputDatabase = "crawl_jos",
      inputCollection = "jos_parsedArticles", outputCollection = "jos_topics", appName="fundParse")
      .getSparkSession

    spark.sparkContext.setLogLevel("ERROR")
    val pipeline = "{'$project': {'_id' : 0, 'fundProject' : 1}}"
    val funds = MongoSpark.load(spark.sparkContext).withPipeline(Seq(Document.parse(pipeline)))
      .filter(fundInfo => fundInfo != null && fundInfo.getString("fundProject") != null && !fundInfo.getString("fundProject").equals(""))
      .groupBy(fundInfo => fundInfo.getString("fundProject"))
      .flatMap(fundInfo => {
        val fundDocuments = new ListBuffer[Document]()
        val fundsNo = FundStrParse.fundParse(fundInfo._1).asScala.toList
        fundsNo.foreach(fundNo => {
          val document = new Document()
          document.append("fundNo", fundNo).append("desc", fundInfo)
          fundDocuments.append(document)
        })
        fundDocuments
      })
      .filter(fundInfo => fundInfo != null)
      .groupBy(fundInfo => fundInfo.getString("fundNo"))
  }

}