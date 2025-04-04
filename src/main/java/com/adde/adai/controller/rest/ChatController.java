package com.adde.adai.controller.rest;

import com.adde.adai.model.ConversationIn;
import com.adde.adai.service.AbstractChatService;
import com.adde.adai.service.ConfigurableChatServiceFactory;
import org.springframework.web.bind.annotation.*;

import static com.adde.adai.controller.statics.ChatApis.*;

@RestController
@RequestMapping(CHAT)
public class ChatController {

    private final ConfigurableChatServiceFactory factory;

    public ChatController(ConfigurableChatServiceFactory factory) {
        this.factory = factory;
    }

    @PostMapping(START)
    public void startConversation(@RequestBody ConversationIn conversationIn,
                                  @RequestParam(required = false) String serviceType) {
        AbstractChatService chatService = factory.getChatService(serviceType);
        chatService.start(conversationIn);
    }

    @PostMapping(BY_CONVERSATION_ID)
    public String chat(@PathVariable String conversationId,
                       @RequestBody String message,
                       @RequestParam(required = false) String serviceType) {
        AbstractChatService chatService = factory.getChatService(serviceType);
        return chatService.chat(conversationId, message);
    }

}