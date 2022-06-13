package ai.zhuanzhi.contentparser.nsfc

import ai.zhuanzhi.mongodb.SparkMongoDBUtil
import ai.zhuanzhi.utils.MessyCodeJudgment
import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config.ReadConfig
import org.apache.spark.sql.catalyst.dsl.expressions.StringToAttributeConversionHelper
import org.apache.spark.sql.{DataFrame, SparkSession}

object MergeNstrsAndNsfc {
  def main(args: Array[String]): Unit = {

    val sparkNSTRS = new SparkMongoDBUtil(inputDatabase = "fund_nstrs", outputDatabase = "fund_nstrs",
      inputCollection = "fund_department", outputCollection = "fund_department_all", appName = "NSTRSParse")
      .getSparkSession

    sparkNSTRS.read.format("com.mongodb.spark.sql.DefaultSource").load().createOrReplaceTempView("fund_nstrs")

    //    val NSTRS: DataFrame = sparkNSTRS.sql("select title, creator, creatOrorganization," +
    //          "uploadId, abstractCn, keywordsCn, abstractEn, keywordsEn," +
    //          "projectName, competentOrg, totalaMount, startDate, endDate, fieldId, " +
    //          "classification, kjbgQWAddress, proposalDate from fund_nstrs")
    //    NSTRS.show(10, truncate = false)

    val readConfig = ReadConfig(Map("collection" -> "fund_nsfc", "readPreference.name" -> "secondaryPreferred"), Some(ReadConfig(sparkNSTRS)))

    MongoSpark.load(sparkNSTRS, readConfig).createOrReplaceTempView("NSFC")
    //    NSFC.show(10, truncate = false)
    val messyCodeToEmpty = (abstractCh: String) => {
      if (MessyCodeJudgment.hasMessyCode(abstractCh)) {
        ""
      } else{
        abstractCh
      }
    }
    sparkNSTRS.udf.register("messyCodeToEmpty", messyCodeToEmpty)
    val NSTRSCleanAbs = sparkNSTRS.sql("select title, messyCodeToEmpty(abstract) from NSFC")
    NSTRSCleanAbs.show(100)
    println(NSTRSCleanAbs.count())
    //    val NSFCList = NSFC.rdd.take(1000)
    //

    //
    //    val NSFCWithCleanAbstract = NSFCList.map(nsfc => {
    //      val abstractIndex = nsfc.fieldIndex("abstract")
    //      if(MessyCodeJudgment.hasMessyCode(nsfc.get(abstractIndex).toString)){
    //        ""
    //      }
    //    })
    //    NSFCWithCleanAbstract.foreach(println)
    //    println(NSFCWithCleanAbstract.first())


  }
}
