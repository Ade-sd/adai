package com.adde.adai.service;

import com.adde.adai.domain.entities.ConversationDoc;
import com.adde.adai.mapper.ConversationMapper;
import com.adde.adai.model.ConversationOut;
import com.adde.adai.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ConversationMapper conversationMapper;

    public ConversationService(ConversationRepository conversationRepository, ConversationMapper conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.conversationMapper = conversationMapper;
    }

    public ConversationDoc create(ConversationDoc conversationDoc) {
        return conversationRepository.save(conversationDoc);
    }

    public List<ConversationOut> findAll() {
        return conversationRepository.findAll()
                .stream()
                .map(conversationMapper::toConversationOut)
                .toList();
    }

}
