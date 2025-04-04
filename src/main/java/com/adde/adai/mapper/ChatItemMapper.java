
package com.adde.adai.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatItemMapper {
    ChatItemMapper INSTANCE = Mappers.getMapper(ChatItemMapper.class);

//    @Mapping(source = "content", target = "context")
//    @Mapping(source = "chatId", target = "chatId")
//    @Mapping(target = "id", ignore = true) // Ignoring id since it's auto-generated
//    ConversationItemDoc toChatItemDoc(ChatItemIn chatItemIn);
}
