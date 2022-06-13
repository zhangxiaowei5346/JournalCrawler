package ai.zhuanzhi.contentparser.nsfc

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import com.mongodb.spark.MongoSpark
import org.bson.Document

import java.util
import scala.collection.JavaConversions.{asScalaBuffer, collectionAsScalaIterable}
import scala.collection.mutable

object TopicTagging {

  def main(args: Array[String]): Unit = {

    val spark = new SparkMongoDBUtil(host="127.0.0.1", port="8191" ,inputDatabase = "zhuanzhi", outputDatabase = "zhuanzhi",
      inputCollection = "topic1.0", outputCollection = "topic1.0", appName="topicTagging")
      .getSparkSession

    spark.sparkContext.setLogLevel("ERROR")


    val pipelineProject = "{'$project': {'_id': 1, 'name': 1, 'alias': 1, 'isTech': 1}}"
    val pipelineFilter = "{'$match': {'isTech': true}}"

    var topicId: mutable.Map[String, String] = mutable.Map()
    val topics = MongoSpark.load(spark.sparkContext)
      .withPipeline(Seq(Document.parse(pipelineProject), Document.parse(pipelineFilter)))
    val topicIds = topics.map(topic => {
      (topic.getString("_id"), topic.getString("name"))
    })
//    topicIds.foreach(println)
//    val alias = topics.flatMap(topic => {
//      val alias = topic.get("alias", classOf[java.util.List[String]])
//      if(alias != null && alias.nonEmpty){
//        var aliasList = mutable.Map
//        val length = alias.length
//        for(i <- 0 until length){
//          aliasList += (topic.getString("_id") -> (alias.get(i)))
//        }
//      }else{
//        null
//      }
//    })
//    alias.foreach(println)
//    topics.
//      .map(topic => {
//        val id = topic.getString("_id")
//        val name = topic.getString("name")
//        val alias = topic.get("alias", classOf[java.util.List[String]])
//        var topicList = List[Map[String, String]]
//        topicList += (name -> id)
//
////        if(alias != null){
////          val length = alias.length
////          for (i <- 0 until length){
////            topicId += (alias.get(i) -> ("A"+id))
////          }
////        }
//      })


//    topicId.foreach(println)



//    topics.foreach(println)
//    println(topics.count())

  }

}
