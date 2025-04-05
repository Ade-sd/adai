package com.adde.adai.controller.rest;

import com.adde.adai.model.PromptIn;
import com.adde.adai.model.PromptOut;
import com.adde.adai.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.adde.adai.controller.statics.PromptApis.*;

@RestController
@RequestMapping(PROMPT)
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping
    public ResponseEntity<PromptOut> create(@RequestBody PromptIn promptIn) {
        PromptOut promptOut = promptService.create(promptIn);
        return ResponseEntity.ok(promptOut);
    }

    @PutMapping(BY_ID)
    public ResponseEntity<PromptOut> update(@PathVariable String id, @RequestBody PromptIn promptIn) {
        PromptOut update = promptService.update(id, promptIn);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        promptService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(BY_NAME)
    public ResponseEntity<String> getById(@PathVariable String name) {
        String byName = promptService.getByName(name);
        return ResponseEntity.ok(byName);
    }

    @GetMapping
    public ResponseEntity<List<PromptOut>> getAll() {
        List<PromptOut> promptOuts = promptService.getAll();
        return ResponseEntity.ok(promptOuts);
    }

}
