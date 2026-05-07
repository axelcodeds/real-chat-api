package dev.axeldiego.realchatapi.mapper;

import dev.axeldiego.realchatapi.dto.MessageDto;
import dev.axeldiego.realchatapi.entity.Message;

public class MessageMapper {
    public static MessageDto toDto(Message m) {
        if (m == null) return null;
        return MessageDto.builder()
                .id(m.getId())
                .conversationId(m.getConversationId())
                .senderId(m.getSenderId())
                .content(m.getContent())
                .createdAt(m.getCreatedAt())
                .build();
    }

    public static Message toEntity(MessageDto dto) {
        if (dto == null) return null;
        return Message.builder()
                .id(dto.getId())
                .conversationId(dto.getConversationId())
                .senderId(dto.getSenderId())
                .content(dto.getContent())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}

