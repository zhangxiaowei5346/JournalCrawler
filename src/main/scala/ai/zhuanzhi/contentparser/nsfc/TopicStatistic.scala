package ai.zhuanzhi.contentparser.nsfc

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import com.mongodb.spark.MongoSpark
import org.bson.Document

import scala.collection.JavaConversions.iterableAsScalaIterable
import scala.collection.JavaConverters._

/*
* fund keywords statistic
* */

object TopicStatistic {
  def main(args: Array[String]): Unit = {

    val documentsSpark = new SparkMongoDBUtil("122.11.50.27", "27017",
      inputDatabase = "zhuanzhi", inputCollection = "document", appName = "topicStatistic")
      .getSparkSession
    documentsSpark.sparkContext.setLogLevel("ERROR")

    val pipelineProject = "{'$project': {'source': 1,'topicKTM': 1,'key': 1}}"
    val pipelineFilter = "{'$match': {'source': 'autofund', 'key': {'$regex':'^6[0-9]{7}$'}}}"

    val documents = MongoSpark.load(documentsSpark.sparkContext)
      .withPipeline(Seq(Document.parse(pipelineProject), Document.parse(pipelineFilter)))

    println(documents.count())

    val topics = documents.filter(document => {
      document.get("topicKTM") != null
    }).flatMap(document => {
      document.get("topicKTM", classOf[java.util.ArrayList[Document]]).toIterable
    }).map(document=>{
      (document.getString("name"), 1)
    }).reduceByKey(_+_)
      .sortBy(topic=>topic._2, ascending = false, numPartitions = 1)

    topics.foreach(println)

    documentsSpark.close()

  }
}
