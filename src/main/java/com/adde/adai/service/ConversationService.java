package com.adde.adai.service;

import com.adde.adai.domain.model.ConversationDoc;
import com.adde.adai.service.repository.ConversationRepository;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public ConversationDoc create(ConversationDoc conversationDoc) {
        return conversationRepository.save(conversationDoc);
    }

}
