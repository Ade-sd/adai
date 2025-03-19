package com.adde.adai.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/prompts")
    public String showPromptsPage() {
        return "prompts"; // Renders prompts.html
    }

}
