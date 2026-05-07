package dev.axeldiego.realchatapi.repository;

import dev.axeldiego.realchatapi.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
    Optional<Conversation> findByParticipantIds(List<String> participantIds);
    List<Conversation> findByParticipantIdsContaining(String participantId);
}

