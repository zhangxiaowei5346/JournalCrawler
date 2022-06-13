package ai.zhuanzhi.contentparser.jos

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import ai.zhuanzhi.tool.dal.dao.MongoTopicDao
import ai.zhuanzhi.utils.EmailStrParse
import com.mongodb.spark.MongoSpark
import ai.zhuanzhi.utils.EmailStrParse
import org.bson.Document

import java.util
import java.util.Date


object AuthorParse {

  def main(args: Array[String]): Unit = {

    val spark = new SparkMongoDBUtil(inputDatabase = "crawl_jos", outputDatabase = "crawl_jos",
      inputCollection = "jos_parsedArticles", outputCollection = "jos_topics", appName="authorParse")
      .getSparkSession

    spark.sparkContext.setLogLevel("ERROR")

    val pipeline1 = "{'$project': {'_id' : 0}}"
    val pipeline2 = "{'$unwind': '$authors'}"
    val pipeline3 = "{'$project': { zhName: '$authors.zhName', email: '$authors.email', znInstitution: '$authors.znInstitution'}}"

    val authorInfo = MongoSpark.load(spark.sparkContext).withPipeline(Seq(Document.parse(pipeline1), Document.parse(pipeline2), Document.parse(pipeline3)))
      .filter(authorInfo => authorInfo.getString("zhName") != null && !authorInfo.getString("email").equals("")
        && authorInfo.getString("email") != null && !authorInfo.getString("email").equals("")
        && authorInfo.getString("znInstitution") != null && !authorInfo.getString("znInstitution").equals(""))
      .map(authorInfo => {
        val email = authorInfo.getString("email")
        val emailParsed = EmailStrParse.emailParse(email)
        if (emailParsed != null) {
          val author = new Document()
          author.append("zhName", authorInfo.getString("zhName"))
            .append("email", emailParsed)
            .append("znInstitution", authorInfo.getString("znInstitution"))
            .append("key", authorInfo.getString("zhName") + "_" + emailParsed)
          author
        } else {
          null
        }
      })
      .filter(authorInfo => authorInfo != null)
      .groupBy(authorInfo => authorInfo.getString("key"))
      .foreach(authorInfo => {
        MongoTopicDao.createAutoTopic(authorInfo._2.head.getString("zhName") + "_" + authorInfo._2.head.getString("email"),
          authorInfo._2.head.getString("zhName"), authorInfo._2.head.getString("zhName") + ", "  + authorInfo._2.head.getString("znInstitution"))
      })
  }
}
