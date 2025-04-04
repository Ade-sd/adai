package com.adde.adai.service;

import com.adde.adai.domain.entities.ConversationDoc;
import com.adde.adai.domain.entities.ConversationItemDoc;
import com.adde.adai.domain.entities.ConversationType;
import com.adde.adai.model.ConversationIn;
import com.adde.adai.model.MessageProcessIn;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public abstract class AbstractChatService {

    private final ConversationService conversationService;
    private final ConversationItemService conversationItemService;
    private final PromptService promptService;

    public AbstractChatService(ConversationService conversationService, ConversationItemService conversationItemService, PromptService promptService) {
        this.conversationService = conversationService;
        this.conversationItemService = conversationItemService;
        this.promptService = promptService;
    }

    public void start(ConversationIn conversationIn) {
        log.info("Starting assistant...");
        ConversationDoc conversation =
                ConversationDoc.builder()
                        .name(conversationIn.getName())
                        .build();
        conversation = conversationService.create(conversation);
        String systemMessage = promptService.getPromptByName("systemMessage");

        if (systemMessage == null) {
            throw new RuntimeException("systemMessage is null");
        }

        ConversationItemDoc messageItem = ConversationItemDoc.builder()
                .message(systemMessage)
                .type(ConversationType.SYSTEM_MESSAGE)
                .conversationId(conversation.getId())
                .build();
        conversationItemService.create(messageItem);
        log.info("Conversation started");
    }

    public String chat(String conversationId, String message) {
        log.info("Start chat for conversationId:  {}", conversationId);
        List<ConversationItemDoc> oldMessages = conversationItemService.findByConversationId(conversationId);

        ConversationItemDoc messageItem = ConversationItemDoc.builder()
                .conversationId(conversationId)
                .type(ConversationType.USER_MESSAGE)
                .message(message)
                .build();
        conversationItemService.create(messageItem);

        return processMessages(MessageProcessIn.builder()
                .oldMessages(oldMessages)
                .lastMessage(messageItem)
                .build());
    }

    public List<Message> getMessagesHistory(@NotNull List<ConversationItemDoc> conversationItems) {
        return conversationItems.stream()
                .map(oldMessage -> switch (oldMessage.getType()) {
                    case SYSTEM_MESSAGE -> new SystemMessage(oldMessage.getMessage());
                    case USER_MESSAGE -> new UserMessage(oldMessage.getMessage());
                    case ASSISTANT_MESSAGE -> new AssistantMessage(oldMessage.getMessage());
                })
                .collect(Collectors.toList());
    }

    public abstract String processMessages(MessageProcessIn messageProcessIn);

}
