package com.adde.adai.repository;

import com.adde.adai.domain.entities.ConversationDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends MongoRepository<ConversationDoc, String> {
}
