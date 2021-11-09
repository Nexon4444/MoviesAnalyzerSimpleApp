import org.apache.spark.sql.SparkSession

object ConnectionManager {
  def main(args: Array[String]): Unit = {
    //SparkSession.builder().master("local[*]").appName("Test").getOrCreate
    val spark = SparkSession.builder().master("spark://localhost:8080").appName("Movies").getOrCreate
//    Class.forName("com.mysql.jdbc.Driver")
//    Class.forName("com.example.jdbc.Driver")
//    val linksDf = spark.read
//      .format("jdbc")
//      .option("url", "jdbc:mysql")
//      .option("dbtable", "LINKS")
//      .option("user", "root")
//      .option("password", "password")
//      .load()
//
//
//    val prop = new java.util.Properties()
//    prop.put("user", "root")
//    prop.put("password", "password")
////    prop.setProperty("driver", "oracle.jdbc.driver.OracleDriver")
//    val url = "jdbc:mysql://127.0.0.1:3306/movies"
    val prop=new java.util.Properties()
    prop.put("user","root")
    prop.put("password","password")
    val url="jdbc:mysql://127.0.0.1:3306/MOVIES"

    val df=spark.read.jdbc(url,"links",prop)
    print(df.take(1).mkString("Array(", ", ", ")"))
//    df.show()
  }
}
