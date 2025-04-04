package com.adde.adai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConfigurableChatServiceFactory {

    private final Map<String, AbstractChatService> chatServices = new HashMap<>();
    private final String defaultServiceType;

    @Autowired
    public ConfigurableChatServiceFactory(
            List<AbstractChatService> serviceList,
            @Value("${chat.service.default}") String defaultServiceType) {

        this.defaultServiceType = defaultServiceType;

        for (AbstractChatService service : serviceList) {
            String serviceType = service.getClass().getSimpleName();
            chatServices.put(serviceType, service);

            if (serviceType.endsWith("ChatService")) {
                String shortName = serviceType.substring(0, serviceType.length() - "ChatService".length());
                chatServices.put(shortName, service);
            }
        }
    }

    public AbstractChatService getChatService(String serviceType) {
        AbstractChatService service = chatServices.get(serviceType);
        if (service == null) {
            return chatServices.getOrDefault(defaultServiceType,
                    chatServices.values().iterator().next());
        }
        return service;
    }

    public Map<String, AbstractChatService> getAllServices() {
        return new HashMap<>(chatServices);
    }
}