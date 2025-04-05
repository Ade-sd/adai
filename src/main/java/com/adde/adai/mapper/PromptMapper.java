package com.adde.adai.mapper;

import com.adde.adai.domain.entities.PromptDoc;
import com.adde.adai.model.PromptIn;
import com.adde.adai.model.PromptOut;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PromptMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "content", target = "content")
    PromptOut toPromptOut(PromptDoc prompt);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "content", target = "content")
    PromptDoc toPromptDoc(PromptIn promptIn);

    void update(PromptIn dto, @MappingTarget PromptDoc entity);
}
