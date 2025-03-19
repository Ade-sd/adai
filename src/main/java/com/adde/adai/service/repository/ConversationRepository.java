package com.adde.adai.service.repository;

import com.adde.adai.domain.model.ConversationDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends MongoRepository<ConversationDoc, String> {
}
