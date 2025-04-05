package com.adde.adai.mapper;

import com.adde.adai.domain.entities.ConversationDoc;
import com.adde.adai.model.ConversationIn;
import com.adde.adai.model.ConversationOut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ConversationMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    ConversationOut toConversationOut(ConversationDoc conversation);

    @Mapping(source = "name", target = "name")
    ConversationDoc toConversationDoc(ConversationIn conversationIn);

}
