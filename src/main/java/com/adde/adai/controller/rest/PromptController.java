package com.adde.adai.controller.rest;

import com.adde.adai.model.PromptIn;
import com.adde.adai.model.PromptOut;
import com.adde.adai.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prompts")
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping
    public PromptOut createPrompt(@RequestBody PromptIn promptIn) {
        return promptService.createPrompt(promptIn);
    }

    @PutMapping("/{name}")
    public PromptOut updatePrompt(@PathVariable String name, @RequestBody PromptIn promptIn) {
        return promptService.updatePrompt(name, promptIn);
    }

    @DeleteMapping("/{name}")
    public void deletePrompt(@PathVariable String name) {
        promptService.deletePrompt(name);
    }

    @GetMapping("/{name}")
    public String getPromptWithParams(@PathVariable String name) {
        return promptService.getPromptByName(name);
    }

    @GetMapping
    public List<String> getPrompts() {
        return promptService.getPrompts();
    }

}
