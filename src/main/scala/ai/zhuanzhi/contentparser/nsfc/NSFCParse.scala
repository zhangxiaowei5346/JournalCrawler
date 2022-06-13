package ai.zhuanzhi.contentparser.nsfc

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import ai.zhuanzhi.webcollector.NSFC.NSFCHtmlParse
import com.mongodb.spark.MongoSpark
import com.zhuanzhi.webcollector.NSFC.NSFCHtmlParse
import org.bson.Document

object NSFCParse {

  def main(args: Array[String]): Unit = {
    val spark = new SparkMongoDBUtil(inputDatabase = "crawl_fund", outputDatabase = "crawl_fund",
      inputCollection = "fund_nsfcPage", outputCollection = "fund_parsedNSFC", appName="nsfcParse")
      .getSparkSession

    spark.sparkContext.setLogLevel("ERROR")

    val pipeline = "{'$project': {'_id' : 0, 'html' : 1}}"

    val nsfc = MongoSpark.load(spark.sparkContext).withPipeline(Seq(Document.parse(pipeline)))
      .filter(nsfcHtml => nsfcHtml != null && nsfcHtml.getString("html") != null && !nsfcHtml.getString("html").equals(""))
      .map(nsfcHtml => NSFCHtmlParse.parse(nsfcHtml.getString("html")))
      .groupBy(nsfcFund => nsfcFund.getString("fundNo"))
      .map(nsfcFund => nsfcFund._2.head)
    MongoSpark.save(nsfc)

  }

}
