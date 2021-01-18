package com.example.health_care.KafkaProducer

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

class Consumer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val TOPIC_NAME = "login"
            val GROUP_ID = "testgroup"
            val BOOTSTRAP_SERVERS = "13.209.87.167:9092"

            val configs = Properties()
            configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
            configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID)
            configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
            configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false)

            val consumer = KafkaConsumer<String, String>(configs)

            consumer.subscribe(Arrays.asList(TOPIC_NAME))

            while (true) {
                val records = consumer.poll(Duration.ofSeconds(1))
                for (record in records) {
                    println(record.value())
                }
                consumer.commitSync() // sync commit을 위한 설정
            }
        }
    }
}