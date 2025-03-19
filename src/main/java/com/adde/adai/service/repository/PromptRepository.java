package com.adde.adai.service.repository;

import com.adde.adai.domain.model.PromptDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromptRepository extends MongoRepository<PromptDoc, String> {
    Optional<PromptDoc> findByName(String name);
}
