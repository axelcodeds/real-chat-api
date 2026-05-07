package dev.axeldiego.realchatapi.service;

import dev.axeldiego.realchatapi.dto.ConversationDto;

import java.util.List;

public interface ConversationService {
    ConversationDto createConversation(List<String> participantIds);
    ConversationDto getConversationById(String id);
    List<ConversationDto> getConversationsByUserId(String userId);
}

