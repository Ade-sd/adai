package com.adde.adai.service;

import com.adde.adai.domain.entities.ConversationItemDoc;
import com.adde.adai.repository.ConversationItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationItemService {

    private final ConversationItemRepository conversationItemRepository;

    public ConversationItemService(ConversationItemRepository conversationItemRepository) {
        this.conversationItemRepository = conversationItemRepository;
    }

    public ConversationItemDoc create(ConversationItemDoc conversationItemDoc) {
        return conversationItemRepository.save(conversationItemDoc);
    }

    public List<ConversationItemDoc> findByConversationId(String conversationId) {
        return conversationItemRepository.findByConversationId(conversationId);
    }


}
