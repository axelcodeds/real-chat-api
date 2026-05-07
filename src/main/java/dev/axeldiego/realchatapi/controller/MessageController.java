package dev.axeldiego.realchatapi.controller;

import dev.axeldiego.realchatapi.dto.CreateMessageRequest;
import dev.axeldiego.realchatapi.dto.MessageDto;
import dev.axeldiego.realchatapi.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(@RequestBody CreateMessageRequest request) {
        MessageDto dto = messageService.sendMessage(request);
        return ResponseEntity.created(URI.create("/api/messages/" + dto.getId())).body(dto);
    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<MessageDto>> getMessagesByConversationId(@PathVariable String conversationId) {
        return ResponseEntity.ok(messageService.getMessagesByConversationId(conversationId));
    }
}

