package com.adde.adai.model;

import com.adde.adai.domain.entities.ConversationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversationItemOut {
    private String id;
    private int messageNumber;
    private String message;
    private ConversationType type;
}
