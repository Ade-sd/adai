package com.adde.adai.service;

import com.adde.adai.domain.entities.PromptDoc;
import com.adde.adai.mapper.PromptMapper;
import com.adde.adai.model.PromptIn;
import com.adde.adai.model.PromptOut;
import com.adde.adai.repository.PromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PromptService {

    private final PromptRepository promptRepository;
    private final PromptMapper promptMapper;

    public PromptService(PromptRepository promptRepository, PromptMapper promptMapper) {
        this.promptRepository = promptRepository;
        this.promptMapper = promptMapper;
    }

    @CacheEvict(value = "prompts", allEntries = true)
    public PromptOut create(PromptIn promptIn) {
        PromptDoc promptDoc = promptMapper.toPromptDoc(promptIn);
        PromptDoc saved = promptRepository.save(promptDoc);
        return promptMapper.toPromptOut(saved);
    }

    @CacheEvict(value = "prompts", allEntries = true)
    public PromptOut update(String id, PromptIn promptIn) {
        Optional<PromptDoc> optionalPrompt = promptRepository.findById(id);
        if (optionalPrompt.isEmpty()) {
            throw new RuntimeException("Prompt not found");
        }
        PromptDoc entity = optionalPrompt.get();
        promptMapper.update(promptIn, entity);

        PromptDoc updated = promptRepository.save(entity);
        return promptMapper.toPromptOut(updated);
    }

    @CacheEvict(value = "prompts", allEntries = true)
    public void delete(String id) {
        PromptDoc prompt = promptRepository.findById(id)
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
    public String getByName(String name) {
        PromptDoc prompt = promptRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Prompt not found"));
        return prompt.getContent();
    }

    @Cacheable(value = "prompts")
    public List<PromptOut> getAll() {
        return promptRepository.findAll()
                .stream()
                .map(promptMapper::toPromptOut)
                .toList();
    }
}
