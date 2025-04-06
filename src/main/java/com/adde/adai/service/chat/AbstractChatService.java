package com.adde.adai.service.chat;

import com.adde.adai.domain.entities.ConversationDoc;
import com.adde.adai.domain.entities.ConversationItemDoc;
import com.adde.adai.domain.entities.ConversationType;
import com.adde.adai.mapper.ConversationMapper;
import com.adde.adai.model.ChatIn;
import com.adde.adai.model.ConversationIn;
import com.adde.adai.model.MessageProcessIn;
import com.adde.adai.service.ConversationItemService;
import com.adde.adai.service.ConversationService;
import com.adde.adai.service.PromptService;
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
    private final ConversationMapper conversationMapper;

    public AbstractChatService(ConversationService conversationService, ConversationItemService conversationItemService, PromptService promptService, ConversationMapper conversationMapper) {
        this.conversationService = conversationService;
        this.conversationItemService = conversationItemService;
        this.promptService = promptService;
        this.conversationMapper = conversationMapper;
    }

    public String start(ConversationIn conversationIn) {
        log.info("Starting chat service...");
        ConversationDoc conversation = conversationMapper.toConversationDoc(conversationIn);
        conversation = conversationService.create(conversation);

        String systemMessage = promptService.getByName("SystemMessage");
        ConversationItemDoc messageItem = ConversationItemDoc.builder()
                .message(systemMessage)
                .type(ConversationType.SYSTEM_MESSAGE)
                .conversationId(conversation.getId())
                .build();
        conversationItemService.create(messageItem);
        log.info("Chat service started.");
        return conversation.getId();
    }

    public String chat(String conversationId, ChatIn chatIn) {
        List<ConversationItemDoc> oldMessages = conversationItemService.findDocumentByConversationId(conversationId);

        ConversationItemDoc messageItem = ConversationItemDoc.builder()
                .conversationId(conversationId)
                .type(ConversationType.USER_MESSAGE)
                .message(chatIn.message())
                .messageNumber(getCurrentMessageNumber(oldMessages))
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

    public int getCurrentMessageNumber(@NotNull List<ConversationItemDoc> conversationItems) {
        int lastMessageNumber = conversationItems.stream()
                .mapToInt(ConversationItemDoc::getMessageNumber)
                .max()
                .orElse(0);
        return lastMessageNumber + 1;
    }

    public abstract String processMessages(MessageProcessIn messageProcessIn);

}
