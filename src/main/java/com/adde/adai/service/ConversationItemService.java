package com.adde.adai.service;

import com.adde.adai.domain.entities.ConversationItemDoc;
import com.adde.adai.mapper.ConversationItemMapper;
import com.adde.adai.model.ConversationItemOut;
import com.adde.adai.repository.ConversationItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationItemService {

    private final ConversationItemRepository conversationItemRepository;
    private final ConversationItemMapper conversationItemMapper;

    public ConversationItemService(ConversationItemRepository conversationItemRepository, ConversationItemMapper conversationItemMapper) {
        this.conversationItemRepository = conversationItemRepository;
        this.conversationItemMapper = conversationItemMapper;
    }

    public ConversationItemDoc create(ConversationItemDoc conversationItemDoc) {
        return conversationItemRepository.save(conversationItemDoc);
    }

    public List<ConversationItemDoc> findDocumentByConversationId(String conversationId) {
        return conversationItemRepository.findByConversationId(conversationId);
    }

    public List<ConversationItemOut> findByConversationId(String conversationId) {
        return conversationItemRepository.findByConversationId(conversationId)
                .stream()
                .map(conversationItemMapper::toConversationItemOut)
                .toList();
    }

}
