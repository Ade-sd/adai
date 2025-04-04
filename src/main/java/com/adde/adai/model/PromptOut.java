package com.adde.adai.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromptOut {
    private String id;
    private String name;
    private String content;
}
