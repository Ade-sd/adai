package com.adde.adai.domain.entities;

import com.adde.adai.domain.entities.enums.ConversationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "conversationItems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationItemDoc {
    @Id
    private String id;
    private String conversationId;
    private String message;
    private int messageNumber;
    private ConversationType type;
    private Map<String, Object> metadata;
}
