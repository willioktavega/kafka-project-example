package com.example.project.kafka.meetup

import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql._


object MeetupRsvpConsumer extends App {
  val TOPIC="test"
  val spark: SparkSession =
    SparkSession.builder()
      .master("local")
      .appName(this.getClass.getName)
      .getOrCreate()

  import spark.implicits._
  spark.sparkContext.setLogLevel("WARN")

  //Subscribe stream from Kafka
  val df = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "test")
    .option("startingOffsets", "latest")
//    .option("endingOffsets", """{"test":{"0":50,"1":-1}}""")
    .option("groupid","None")
    .option("failOnDataLoss", false)
    .load()

  val schema = new StructType()
    .add("venue", new StructType()
      .add("venue_name", StringType)
      .add("lan", DoubleType)
      .add("lat", DoubleType)
      .add("venue_id", LongType)
    )
    .add("visibility", StringType)
    .add("response", StringType)
    .add("guests", IntegerType)
    .add("member", new StructType()
      .add("member_id", LongType)
      .add("photo", StringType)
      .add("member_name", StringType)
    )
    .add("rsvp_id", LongType)
    .add("mtime", LongType)
//    .add("event", new StructType()
//      .add("event_name", StringType)
//      .add("event_id", LongType)
//      .add("time", LongType)
//      .add("event_url", StringType)
//    )

  // for stream
 val query1 = df.select($"topic", $"value" cast "string" as "json")
      .select($"topic", from_json($"json", schema) as "data")
      .select($"topic", $"data.*")
    .writeStream
//    .outputMode("append")
    .format("console")
    .start()

// val query2 = df.selectExpr("CAST(topic AS STRING)", "CAST(value AS STRING)")
//   .as[(String, String)]
//   .writeStream
//   .format("console")
//   .start()

 df.printSchema()
 query1.awaitTermination()
// query2.awaitTermination()
}
