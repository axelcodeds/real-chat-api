package dev.axeldiego.realchatapi.service.impl;

import dev.axeldiego.realchatapi.dto.CreateMessageRequest;
import dev.axeldiego.realchatapi.dto.MessageDto;
import dev.axeldiego.realchatapi.entity.Conversation;
import dev.axeldiego.realchatapi.entity.Message;
import dev.axeldiego.realchatapi.mapper.MessageMapper;
import dev.axeldiego.realchatapi.repository.ConversationRepository;
import dev.axeldiego.realchatapi.repository.MessageRepository;
import dev.axeldiego.realchatapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    @Override
    public MessageDto sendMessage(CreateMessageRequest request) {
        if (request.getSenderId() == null || request.getRecipientId() == null) {
            throw new IllegalArgumentException("senderId and recipientId are required");
        }

        List<String> participants = new ArrayList<>();
        participants.add(request.getSenderId());
        participants.add(request.getRecipientId());
        Collections.sort(participants);

        Conversation conversation = conversationRepository.findByParticipantIds(participants)
                .orElseGet(() -> {
                    Conversation conv = Conversation.builder()
                            .participantIds(participants)
                            .lastMessage(null)
                            .lastMessageAt(null)
                            .build();
                    return conversationRepository.save(conv);
                });

        Message m = Message.builder()
                .conversationId(conversation.getId())
                .senderId(request.getSenderId())
                .content(request.getContent())
                .createdAt(Instant.now())
                .build();
        Message saved = messageRepository.save(m);

        // update conversation
        conversation.setLastMessage(saved.getContent());
        conversation.setLastMessageAt(saved.getCreatedAt());
        conversationRepository.save(conversation);

        return MessageMapper.toDto(saved);
    }

    @Override
    public List<MessageDto> getMessagesByConversationId(String conversationId) {
        return messageRepository.findByConversationIdOrderByCreatedAtAsc(conversationId)
                .stream()
                .map(MessageMapper::toDto)
                .collect(Collectors.toList());
    }
}

