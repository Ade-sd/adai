package com.adde.adai.service;

import com.adde.adai.domain.entities.ConversationItemDoc;
import com.adde.adai.domain.entities.ConversationType;
import com.adde.adai.model.MessageProcessIn;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleChatService extends AbstractChatService {

    private final ChatModel chatModel;
    private final ConversationItemService conversationItemService;

    public SimpleChatService(ConversationService conversationService, ConversationItemService conversationItemService, PromptService promptService, ChatModel chatModel) {
        super(conversationService, conversationItemService, promptService);
        this.chatModel = chatModel;
        this.conversationItemService = conversationItemService;
    }

    @Override
    public String processMessages(MessageProcessIn messageProcessIn) {
        List<ConversationItemDoc> conversationItemDocs = mergeConversationItems(messageProcessIn);
        List<Message> messagesHistory = getMessagesHistory(conversationItemDocs);
        Prompt prompt = new Prompt(messagesHistory);
        ChatResponse response = chatModel.call(prompt);

        if (response == null) {
            throw new RuntimeException("Null response from chat model");
        }
        String responseText = response.getResult().getOutput().getText();
        ConversationItemDoc conversationItem = ConversationItemDoc.builder()
                .type(ConversationType.ASSISTANT_MESSAGE)
                .conversationId(messageProcessIn.getLastMessage().getConversationId())
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
