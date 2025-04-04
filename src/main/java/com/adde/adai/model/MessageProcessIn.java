package com.adde.adai.model;

import com.adde.adai.domain.entities.ConversationItemDoc;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageProcessIn {
    private List<ConversationItemDoc> oldMessages;
    private ConversationItemDoc lastMessage;
}
