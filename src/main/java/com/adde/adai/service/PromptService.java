package com.adde.adai.service;

import com.adde.adai.domain.dto.PromptIn;
import com.adde.adai.domain.dto.PromptOut;
import com.adde.adai.domain.model.PromptDoc;
import com.adde.adai.service.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;

    @CacheEvict(value = "prompts", allEntries = true)
    public PromptOut createPrompt(PromptIn promptIn) {
        PromptDoc entity = PromptDoc.builder()
                .content(promptIn.getContent())
                .build();
        PromptDoc saved = promptRepository.save(entity);
        return new PromptOut(saved.getId(), saved.getName(), saved.getContent());
    }

    @CacheEvict(value = "prompts", allEntries = true)
    public PromptOut updatePrompt(String name, PromptIn promptIn) {
        Optional<PromptDoc> optionalPrompt = promptRepository.findByName(name);
        if (optionalPrompt.isEmpty()) {
            throw new RuntimeException("Prompt not found");
        }
        PromptDoc existingPrompt = optionalPrompt.get();
        existingPrompt.setContent(promptIn.getContent());

        PromptDoc updated = promptRepository.save(existingPrompt);
        return new PromptOut(updated.getId(), updated.getName(), updated.getContent());
    }

    @CacheEvict(value = "prompts", allEntries = true)
    public void deletePrompt(String name) {
        PromptDoc prompt = promptRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Prompt not found"));
        promptRepository.delete(prompt);
    }

    @Cacheable(value = "prompts", key = "#id")
    public String getPromptWithParams(String name, Map<String, String> params) {
        PromptDoc prompt = promptRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Prompt not found"));

        String content = prompt.getContent();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            content = content.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return content;
    }

    @Cacheable(value = "prompts", key = "#name")
    public String getPromptByName(String name) {
        PromptDoc prompt = promptRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Prompt not found"));
        return prompt.getContent();
    }

    @Cacheable(value = "prompts")
    public List<String> getPrompts() {
        return promptRepository.findAll().stream().map(PromptDoc::getName).toList();
    }
}
