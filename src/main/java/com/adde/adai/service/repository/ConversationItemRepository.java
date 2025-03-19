package com.adde.adai.service.repository;

import com.adde.adai.domain.model.ConversationItemDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationItemRepository extends MongoRepository<ConversationItemDoc, String> {
}
