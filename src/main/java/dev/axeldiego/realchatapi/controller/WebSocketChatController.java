package dev.axeldiego.realchatapi.controller;

import dev.axeldiego.realchatapi.dto.CreateMessageRequest;
import dev.axeldiego.realchatapi.dto.MessageDto;
import dev.axeldiego.realchatapi.dto.ws.ChatMessageWsDto;
import dev.axeldiego.realchatapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {
    private final MessageService messageService;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageWsDto request) {

        CreateMessageRequest messageRequest = CreateMessageRequest.builder()
                .senderId(request.getSenderId())
                .recipientId(request.getRecipientId())
                .content(request.getContent())
                .build();

        MessageDto createdMessage = messageService.sendMessage(messageRequest);

        messagingTemplate.convertAndSend(
                "/topic/conversations/" + createdMessage.getConversationId(),
                createdMessage
        );
    }
}
