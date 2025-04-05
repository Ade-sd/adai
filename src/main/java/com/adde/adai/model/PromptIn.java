package com.adde.adai.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromptIn {
    private String content;
    private String name;
}
