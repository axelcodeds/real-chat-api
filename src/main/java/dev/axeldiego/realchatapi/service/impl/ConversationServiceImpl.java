package dev.axeldiego.realchatapi.service.impl;

import dev.axeldiego.realchatapi.dto.ConversationDto;
import dev.axeldiego.realchatapi.entity.Conversation;
import dev.axeldiego.realchatapi.exception.ResourceNotFoundException;
import dev.axeldiego.realchatapi.mapper.ConversationMapper;
import dev.axeldiego.realchatapi.repository.ConversationRepository;
import dev.axeldiego.realchatapi.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;

    @Override
    public ConversationDto createConversation(List<String> participantIds) {
        if (participantIds == null || participantIds.isEmpty()) {
            throw new IllegalArgumentException("participantIds must not be empty");
        }
        List<String> ids = new ArrayList<>(participantIds);
        Collections.sort(ids);

        // check existing
        return conversationRepository.findByParticipantIds(ids)
                .map(ConversationMapper::toDto)
                .orElseGet(() -> {
                    Conversation conv = Conversation.builder()
                            .participantIds(ids)
                            .lastMessage(null)
                            .lastMessageAt(null)
                            .build();
                    Conversation saved = conversationRepository.save(conv);
                    return ConversationMapper.toDto(saved);
                });
    }

    @Override
    public ConversationDto getConversationById(String id) {
        Conversation conv = conversationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found with id: " + id));
        return ConversationMapper.toDto(conv);
    }

    @Override
    public List<ConversationDto> getConversationsByUserId(String userId) {
        List<Conversation> list = conversationRepository.findByParticipantIdsContaining(userId);
        return list.stream().map(ConversationMapper::toDto).collect(Collectors.toList());
    }

    // helper for message service to update last message
    public Conversation updateLastMessage(Conversation conversation, String lastMessage, Instant at) {
        conversation.setLastMessage(lastMessage);
        conversation.setLastMessageAt(at);
        return conversationRepository.save(conversation);
    }
}

