package qaproducer;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class producerWExcel {
    public static void main(String[] args) throws InterruptedException, IOException, ParseException {
        
        final Logger logger = LoggerFactory.getLogger(producer.class);
        // create a Properties instance with the required Kafka configuration
        logger.info("Defining Kafka properties");
        Properties kafkaProps = new Properties();
       
        //logger.info("Setting up bootstrap server's url");
         kafkaProps.setProperty("bootstrap.servers","b-2.amazonaws.com:9098,"
         		+ "b-4.amazonaws.com:9098,"
         		+ "b-1.amazonaws.com:9098");
      
     
        logger.info("Setting secutiry protool to SSL");
        kafkaProps.setProperty("security.protocol", "SASL_SSL");

     
        kafkaProps.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

   
        kafkaProps.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        logger.info("Setting sasl mechanism to use IAM");
        kafkaProps.setProperty("sasl.mechanism", "AWS_MSK_IAM");

        logger.info("Setting sasl jass to use Iam module");
        kafkaProps.setProperty("sasl.jaas.config", "software.amazon.msk.auth.iam.IAMLoginModule required;");

        logger.info("setting call back handler provided by amazon");
        kafkaProps.setProperty("sasl.client.callback.handler.class",
                "software.amazon.msk.auth.iam.IAMClientCallbackHandler");

        kafkaProps.setProperty("ssl.truststore.location", "./qaproducer/kafka.client.truststore.jks");
        //kafkaProps.setProperty("ssl.trustore.password", null);
        
        ExcelReader reader = new ExcelReader();
        List<String> msgLst = reader.ReadExcel();

        KafkaProducer<String, String> producer = new KafkaProducer<String,String>(kafkaProps);

        String topic = "raw-hl7";
       
        for (String msg : msgLst){
            ProducerRecord<String,String> record = new ProducerRecord<String, String>
                        (topic, "testing", msg);
        
            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if (e == null) {//check the data was successfully written or not
                        //return metadata
                        System.out.println("Messages are successfully sent");
                        System.out.println("topic: " + recordMetadata.topic() + "   "
                        + recordMetadata.partition() + "  "
                        + recordMetadata.offset()+ "  "
                        + recordMetadata.timestamp());
                    

                } else {
                  System.out.println("Error Occured" + e );
                }
            }
        });
        
        Thread.sleep(10000); 
		

	}
}
}



