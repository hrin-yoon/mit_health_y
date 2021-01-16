package com.tacademy2

import jdk.nashorn.internal.objects.Global.print
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.sql.DriverManager.println
import java.util.*


 class SimpleProducer2 {

        private val TOPIC_NAME = "login"
        private val BOOTSTRAP_SERVERS = "13.209.87.167:9092"

         fun main(args:Array<String>) {

            val configs = Properties()

            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
            var producer = KafkaProducer<String, String>(configs)

            for (index in 0..9)
            {
                val data = "This is record  =  " + index

                val record : ProducerRecord<String, String> = ProducerRecord(TOPIC_NAME, data)
                try
                {
                    producer.send(record)
                    println("Send to " + TOPIC_NAME + " | data : " + data)
                    Thread.sleep(1000)
                }
                catch (e:Exception) {
                    print("오류")
                }

            }
        }
    }
