package com.example.health_care.KafkaProducer

// import jdk.nashorn.internal.objects.Global.print
import android.os.Bundle
import com.example.health_care.R
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import java.sql.DriverManager.println
import java.util.*

class Producer  {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {


            val TOPIC_NAME : String= "login"
            val BOOTSTRAP_SERVERS :String = "13.209.87.167:9092"

            val configs = Properties()

            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

            val producer = KafkaProducer<String, String>(configs)

            for (index in 0..9) {
                val data = "This is record " + index
                val record = ProducerRecord<String, String>(TOPIC_NAME, data)

                try {
                    producer.send(record)
                    println("Send to " + TOPIC_NAME + "| data :" + data)
                    Thread.sleep(1000)
                } catch (e: Exception) { println(e) }
            }
        }
    }
}

