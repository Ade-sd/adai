package com.adde.adai.service.chat;

import com.adde.adai.domain.entities.ConversationItemDoc;
import com.adde.adai.domain.entities.ConversationType;
import com.adde.adai.mapper.ConversationMapper;
import com.adde.adai.model.MessageProcessIn;
import com.adde.adai.service.AiService;
import com.adde.adai.service.ConversationItemService;
import com.adde.adai.service.ConversationService;
import com.adde.adai.service.PromptService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SimpleChatService extends AbstractChatService {

    private final AiService aiService;
    private final ConversationItemService conversationItemService;

    public SimpleChatService(ConversationService conversationService,
                             ConversationItemService conversationItemService,
                             PromptService promptService,
                             AiService aiService,
                             ConversationMapper conversationMapper) {
        super(conversationService, conversationItemService, promptService, conversationMapper);
        this.aiService = aiService;
        this.conversationItemService = conversationItemService;
    }

    @Override
    public String processMessages(MessageProcessIn messageProcessIn) {
        List<ConversationItemDoc> conversationItemDocs = mergeConversationItems(messageProcessIn);
        List<Message> messagesHistory = getMessagesHistory(conversationItemDocs);
        Prompt prompt = new Prompt(messagesHistory);
        String responseText = aiService.call(prompt);

        ConversationItemDoc conversationItem = ConversationItemDoc.builder()
                .type(ConversationType.ASSISTANT_MESSAGE)
                .conversationId(messageProcessIn.getLastMessage().getConversationId())
                .messageNumber(getCurrentMessageNumber(messageProcessIn.getOldMessages()))
                .message(responseText).build();
        conversationItemService.create(conversationItem);
        return responseText;
    }

    private List<ConversationItemDoc> mergeConversationItems(@NotNull MessageProcessIn messageProcessIn) {
        List<ConversationItemDoc> allMessages = new ArrayList<>(messageProcessIn.getOldMessages());
        allMessages.add(messageProcessIn.getLastMessage());
        return allMessages;
    }

}
