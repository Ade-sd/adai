package com.adde.adai.controller.rest;

import com.adde.adai.model.ConversationIn;
import com.adde.adai.model.ConversationItemOut;
import com.adde.adai.model.ConversationOut;
import com.adde.adai.service.AbstractChatService;
import com.adde.adai.service.ConfigurableChatServiceFactory;
import com.adde.adai.service.ConversationItemService;
import com.adde.adai.service.ConversationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adde.adai.controller.statics.ChatApis.*;

@RestController
@RequestMapping(CHAT)
public class ChatController {

    private final ConfigurableChatServiceFactory factory;
    private final ConversationService conversationService;
    private final ConversationItemService conversationItemService;

    public ChatController(ConfigurableChatServiceFactory factory, ConversationService conversationService, ConversationItemService conversationItemService) {
        this.factory = factory;
        this.conversationService = conversationService;
        this.conversationItemService = conversationItemService;
    }

    @PostMapping(START)
    public ResponseEntity<String> startConversation(@ModelAttribute ConversationIn conversationIn,
                                                    @RequestParam(required = false) String serviceType) {
        AbstractChatService chatService = factory.getChatService(serviceType);
        String conversationId = chatService.start(conversationIn);
        return ResponseEntity.ok(conversationId);
    }

    @PostMapping(BY_CONVERSATION_ID)
    public ResponseEntity<String> chat(@PathVariable String conversationId,
                                       @RequestBody String message,
                                       @RequestParam(required = false) String serviceType) {
        AbstractChatService chatService = factory.getChatService(serviceType);
        String response = chatService.chat(conversationId, message);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConversationOut>> getAllConversations() {
        List<ConversationOut> conversations = conversationService.findAll();
        return ResponseEntity.ok(conversations);
    }

    @GetMapping(BY_CONVERSATION_ID)
    public ResponseEntity<List<ConversationItemOut>> getConversationItemsByConversationId(@PathVariable String conversationId) {
        List<ConversationItemOut> byConversationId = conversationItemService.findByConversationId(conversationId);
        return ResponseEntity.ok(byConversationId);
    }

}