package dev.axeldiego.realchatapi.mapper;

import dev.axeldiego.realchatapi.dto.ConversationDto;
import dev.axeldiego.realchatapi.entity.Conversation;

public class ConversationMapper {
    public static ConversationDto toDto(Conversation c) {
        if (c == null) return null;
        return ConversationDto.builder()
                .id(c.getId())
                .participantIds(c.getParticipantIds())
                .lastMessage(c.getLastMessage())
                .lastMessageAt(c.getLastMessageAt())
                .build();
    }

    public static Conversation toEntity(ConversationDto dto) {
        if (dto == null) return null;
        return Conversation.builder()
                .id(dto.getId())
                .participantIds(dto.getParticipantIds())
                .lastMessage(dto.getLastMessage())
                .lastMessageAt(dto.getLastMessageAt())
                .build();
    }
}

