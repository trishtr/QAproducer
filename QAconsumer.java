package QAConsumer.QAConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class qaConsumer {

	public static void main(String[] args) {
		
	    Properties consProps = new Properties();
	   
	    final Logger logger = LogManager.getLogger(qaConsumer.class);

        final String bootstrapServer = "b-2.amazonaws.com:9098,"
         		+ "b-4.amazonaws.com:9098,"
         		+ "b-1.amazonaws.com:9098";

        //create and populate properties object
        Properties prop = new Properties();
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "qaConsumer");
        prop.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        
        logger.info("Setting secutiry protool to SSL");
        prop.setProperty("security.protocol", "SASL_SSL");

     
        prop.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

   
        prop.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        logger.info("Setting sasl mechanism to use IAM");
        prop.setProperty("sasl.mechanism", "AWS_MSK_IAM");

        logger.info("Setting sasl jass to use Iam module");
        prop.setProperty("sasl.jaas.config", "software.amazon.msk.auth.iam.IAMLoginModule required;");

        logger.info("setting call back handler provided by amazon");
        prop.setProperty("sasl.client.callback.handler.class",
                "software.amazon.msk.auth.iam.IAMClientCallbackHandler");

        prop.setProperty("ssl.truststore.location", "./kafka.client.truststore.jks");
        //kafkaProps.setProperty("ssl.trustore.password", null);
        //create consumer

        final KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(prop);

        //subscribe to topics

        consumer.subscribe(Arrays.asList("raw-hl7"));

        //poll and consume records
        while(true) {
            ConsumerRecords<String, String> records  = consumer.poll(Duration.ofMillis(1000));
            for(ConsumerRecord record: records){
                System.out.println("Topic: " + record.topic());
                System.out.println("Key: " + record.key());
                System.out.println("Partition: " + record.partition());
                System.out.println("Timestamp: " + record.timestamp());
                System.out.println("Offset: " + record.offset());
            }
        }     
	}

}
