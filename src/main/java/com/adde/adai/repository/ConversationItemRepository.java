package com.adde.adai.repository;

import com.adde.adai.domain.entities.ConversationItemDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationItemRepository extends MongoRepository<ConversationItemDoc, String> {
    List<ConversationItemDoc> findByConversationId(String conversationId);
}
