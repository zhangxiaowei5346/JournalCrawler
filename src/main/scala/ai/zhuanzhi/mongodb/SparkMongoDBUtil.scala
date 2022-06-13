package ai.zhuanzhi.mongodb

import org.apache.spark.sql.SparkSession

import java.net.URLEncoder

class SparkMongoDBUtil(var host: String = "172.17.0.2", var port: String = "27017",
                       var userName: String = "zxw", var password: String = "zxw@12345",
                       var authDatabase: String = "admin",
                       var inputDatabase: String = null, var outputDatabase: String = null,
                       var inputCollection: String = null, var outputCollection: String = null, val appName: String) {

  userName = URLEncoder.encode(userName, "utf-8")
  password = URLEncoder.encode(password, "utf-8")

  def getSparkSession: SparkSession = SparkSession.builder()
    .master("local[10]")
    .appName(appName)
    .config("spark.mongodb.input.uri", s"mongodb://$host:$port/")
//    .config("spark.mongodb.output.uri", s"mongodb://$userName:$password@$host:$port/$authDatabase")
    .config("spark.mongodb.output.uri", s"mongodb://$host:$port/")
//    .config("spark.mongodb.output.uri", s"mongodb://$userName:$password@$host:$port/$authDatabase")
//    .config("spark.mongodb.output.database", outputDatabase)
//    .config("spark.mongodb.output.collection", outputCollection)
    .config("spark.mongodb.input.database", inputDatabase)
    .config("spark.mongodb.input.collection", inputCollection)
    .getOrCreate()

}



