package dev.axeldiego.realchatapi.controller;

import dev.axeldiego.realchatapi.dto.ConversationDto;
import dev.axeldiego.realchatapi.dto.CreateConversationRequest;
import dev.axeldiego.realchatapi.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public ResponseEntity<ConversationDto> createConversation(@RequestBody CreateConversationRequest request) {
        ConversationDto dto = conversationService.createConversation(request.getParticipantIds());
        return ResponseEntity.created(URI.create("/api/conversations/" + dto.getId())).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConversationDto> getConversationById(@PathVariable String id) {
        return ResponseEntity.ok(conversationService.getConversationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConversationDto>> getConversationsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(conversationService.getConversationsByUserId(userId));
    }
}

