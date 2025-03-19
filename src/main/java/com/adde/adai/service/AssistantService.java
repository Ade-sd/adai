package com.adde.adai.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AssistantService {
    private final ConversationService conversationService;
    private final ConversationItemService conversationItemService;

    public AssistantService(ConversationService conversationService, ConversationItemService conversationItemService) {
        this.conversationService = conversationService;
        this.conversationItemService = conversationItemService;
    }

    public void startAssistant() {
        log.info("Starting assistant...");

    }


}
