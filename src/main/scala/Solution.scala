import org.apache.spark.sql.{ SaveMode, SparkSession, functions}
import org.apache.spark.sql.functions._

import java.io.{File}
object Solution {
  def deleteUnusedFilesFromDir(path: String): Unit = {
    for {
      dir: File <- new File(path).listFiles()
      files <- Option(dir.listFiles) if dir.isDirectory
      file <- files if file.getName.endsWith(".crc") || file.getName=="_SUCCESS"
    } file.delete()
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local").getOrCreate()
    val prop=new java.util.Properties()
    prop.put("user","root")
    prop.put("password","password")
    val url="jdbc:mysql://127.0.0.1:3306/MOVIES"

    val dfMovies=spark.read.jdbc(url,"movies",prop)
    val dfRatings=spark.read.jdbc(url,"ratings",prop)

    dfMovies.agg(countDistinct("movieId"))
      .write
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("dataAnalyzed/numberOfMovies")

    dfMovies.withColumn("Genres", explode(split(dfMovies.col("genres"),"[|]")))
      .groupBy("Genres")
      .count()
      .sort(desc("count"))
      .select("Genres")
      .limit(1)
      .coalesce(1)
      .write
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("dataAnalyzed/topGenre")

    dfMovies
      .join(dfRatings, Seq("movieId"),"inner")
      .groupBy(col("movieId"))
      .agg(mean(col("rating")))
      .join(dfMovies, Seq("movieId"),"inner")
      .sort(desc("avg(rating)"))
      .select("title","avg(rating)")
      .limit(10)
      .coalesce(1)
      .write
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("dataAnalyzed/top10rated")

    dfRatings
      .groupBy("userId")
      .count()
      .sort(desc("count"))
      .limit(5)
      .write
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("dataAnalyzed/mostActiveUser5")

    val maxMovieId = dfRatings.select(functions.max(struct(
      col("timestamp") +: dfRatings.columns.collect {case x if x!= "timestamp" => col(x)}: _*
    ))).first.getStruct(0)(3)

    dfMovies.select("title").filter(col("movieId") === (maxMovieId))
      .limit(1)
      .write
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("dataAnalyzed/theMostRecentRated")

    val minMovieId = dfRatings.select(functions.min(struct(
      col("timestamp") +: dfRatings.columns.collect {case x if x!= "timestamp" => col(x)}: _*
    ))).first.getStruct(0)(3)

    dfMovies.select("title").filter(col("movieId") === (minMovieId))
      .limit(1)
      .write
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("dataAnalyzed/theOldestRated")

    dfMovies.withColumn("year", regexp_extract(col("title"),"\\((\\d+)\\)", 1))
      .filter(col("year") === "1990")
      .write
      .option("header", "true")
      .mode(SaveMode.Overwrite)
      .csv("dataAnalyzed/moviesIn1990")

    deleteUnusedFilesFromDir("dataAnalyzed")
  }
}