package com.example.health_care.KafkaProducer

// import jdk.nashorn.internal.objects.Global.print
import android.content.Intent
import android.os.Build.USER
import android.os.Bundle
import android.provider.Telephony.Carriers.USER
import android.util.Log.d
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.health_care.Health_data
import com.example.health_care.R
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import com.example.health_care.Health_signUp
import junit.runner.Version.id
import org.apache.kafka.clients.producer.RecordMetadata
import java.sql.DriverManager.println
import java.util.*



internal class Producer {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val TOPIC_NAME: String = "login"
            val BOOTSTRAP_SERVERS: String = "13.209.87.167:9092"

            val configs = Properties()

            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer")
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer")



            val producer = KafkaProducer<String, String>(configs)
            val SP = Health_signUp.User()


            // 제목
            var record: ProducerRecord<String, String> =
                ProducerRecord(TOPIC_NAME, " ===MIT_HEALTH=== ")
            var future = producer.send(record)

            //회원가입 정보
            producer.send(ProducerRecord(TOPIC_NAME, "ID->" + SP.UserID))
            producer.send(ProducerRecord(TOPIC_NAME, "PW->" + SP.UserPW))
            producer.close()


              try {
                   producer.send(ProducerRecord<String, String> (TOPIC_NAME, "Apache Kafka Producer Test"))
               } catch (exception:Exception)
               {
                   exception.printStackTrace()
               }
               finally { producer.close() }


//                System.out.println("ID: " + Health_sign.)
//                System.out.println("PW : " + Health_sign)

        }
    }
}





// for (index in 0..2) {
//val ab = com.example.health_care.Health_signUp.User().UserID
// val cd = com.example.health_care.Health_signUp.User().UserPW
// val data = "This is record = " + index // + ab + cd

// val record = ProducerRecord<String, String>(TOPIC_NAME, data)


// try {
// producer.send(record)
//  println("Send to " + TOPIC_NAME + "| data :" + data)
// Thread.sleep(1000)
// } catch (e: Exception) { println(e) }
//  }


//            try {
//                producer.send(ProducerRecord<String, String>(TOPIC_NAME, "Apache Kafka Producer Test"))
//            } catch (exception:Exception) { exception.printStackTrace() }
//            finally { producer.close() }
