package com.adde.adai.service;

import com.adde.adai.domain.model.ConversationItemDoc;
import com.adde.adai.service.repository.ConversationItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ConversationItemService {

    private final ConversationItemRepository conversationItemRepository;

    public ConversationItemService(ConversationItemRepository conversationItemRepository) {
        this.conversationItemRepository = conversationItemRepository;
    }

    public ConversationItemDoc create(ConversationItemDoc conversationItemDoc) {
        return conversationItemRepository.save(conversationItemDoc);
    }

}
