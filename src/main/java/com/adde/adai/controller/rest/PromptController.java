package com.adde.adai.controller.rest;

import com.adde.adai.model.PromptIn;
import com.adde.adai.model.PromptOut;
import com.adde.adai.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adde.adai.controller.statics.PromptApis.BY_NAME;
import static com.adde.adai.controller.statics.PromptApis.PROMPT;

@RestController
@RequestMapping(PROMPT)
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping
    public PromptOut createPrompt(@RequestBody PromptIn promptIn) {
        return promptService.createPrompt(promptIn);
    }

    @PutMapping(BY_NAME)
    public PromptOut updatePrompt(@PathVariable String name, @RequestBody PromptIn promptIn) {
        return promptService.updatePrompt(name, promptIn);
    }

    @DeleteMapping(BY_NAME)
    public void deletePrompt(@PathVariable String name) {
        promptService.deletePrompt(name);
    }

    @GetMapping(BY_NAME)
    public String getPromptWithParams(@PathVariable String name) {
        return promptService.getPromptByName(name);
    }

    @GetMapping
    public List<String> getPrompts() {
        return promptService.getPrompts();
    }

}
