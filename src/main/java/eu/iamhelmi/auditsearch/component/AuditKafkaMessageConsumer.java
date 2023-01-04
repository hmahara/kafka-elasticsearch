package eu.iamhelmi.auditsearch.component;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import eu.iamhelmi.auditsearch.document.UserAccountDocument;
import eu.iamhelmi.auditsearch.dto.UserAccount;
import eu.iamhelmi.auditsearch.repository.ElasticSearchQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty("kafka.enabled")
@Component
public class AuditKafkaMessageConsumer {
	
	@Autowired
	ElasticSearchQuery query;

//	@KafkaListener(groupId = "${kafka.topic.search-engine.group-id}",
//			topics = "${kafka.topic.search-engine.name}"
//	)
//	void onMessageElasticSearch(@Payload String message, ConsumerRecordMetadata meta) {
//		log.info("UserAccount is created.  [{}] from offset-{} and partition {}", message, meta.offset(), meta.partition());
//	}
	
	@KafkaListener(groupId = "${kafka.topic.useraccount-create.group-id}",
			topics = "${kafka.topic.useraccount-create.name}"
	)
	void onMessageUserAccountCreated(@Payload String message, ConsumerRecordMetadata meta) {
		log.info("ELS Elastic Search UserAccount is created.  [{}] from offset-{} and partition {}", message, meta.offset(), meta.partition());
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			UserAccount ua = objectMapper.readValue(message, UserAccount.class);
			log.info("*** Object is created from String {} ", ua.getUserAccountUUID());
			UserAccountDocument doc = new UserAccountDocument();
			doc.setId(ua.getUserAccountUUID());
			doc.setLogin(ua.getLogin());
			doc.setOrganizationUUID(ua.getOrganizationUUID());
			doc.setPin(ua.getPin());
			doc.setRole(ua.getRole());
			doc.setPassword(ua.getPassword());
			query.createOrUpdateDocument(doc);
			//query.createSingleProduct(doc);
			log.info("**** SUCCESS {} ", ua.getUserAccountUUID());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@KafkaListener(groupId = "${kafka.topic.useraccount-create.group-id}",
			topics = "${kafka.topic.useraccount-update.name}"
	)
	void onMessageUserAccountUpdated(@Payload String message, ConsumerRecordMetadata meta) {
		log.info("ELS Elastic Search  UserAccount is updated.  [{}] from offset-{} and partition {}", message, meta.offset(), meta.partition());

	}
	
	@KafkaListener(groupId = "${kafka.topic.useraccount-create.group-id}",
			topics = "${kafka.topic.useraccount-delete.name}"
	)
	void onMessageUserAccountDeleted(@Payload String message, ConsumerRecordMetadata meta) {
		log.info("ELS Elastic Search  UserAccount is deleted.  [{}] from offset-{} and partition {}", message, meta.offset(), meta.partition());

	}
	

}
