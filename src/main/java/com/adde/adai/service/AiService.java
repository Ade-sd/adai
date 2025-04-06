package com.adde.adai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatModel chatModel;

    public String call(Prompt prompt) {
        log.info("Calling chat model...");
        log.info(prompt.toString());
        ChatResponse response = chatModel.call(prompt);
        if (response == null) {
            throw new RuntimeException("Null response from chat model");
        }
        log.info("Finished chat model call");
        return response.getResult().getOutput().getText();
    }

}
