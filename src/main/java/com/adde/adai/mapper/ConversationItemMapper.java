package com.adde.adai.mapper;

import com.adde.adai.domain.entities.ConversationItemDoc;
import com.adde.adai.model.ConversationItemOut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationItemMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "type", target = "type")
    ConversationItemOut toConversationItemOut(ConversationItemDoc conversationItem);

}
