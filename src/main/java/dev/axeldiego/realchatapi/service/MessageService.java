package dev.axeldiego.realchatapi.service;

import dev.axeldiego.realchatapi.dto.CreateMessageRequest;
import dev.axeldiego.realchatapi.dto.MessageDto;

import java.util.List;

public interface MessageService {
    MessageDto sendMessage(CreateMessageRequest request);
    List<MessageDto> getMessagesByConversationId(String conversationId);
}

