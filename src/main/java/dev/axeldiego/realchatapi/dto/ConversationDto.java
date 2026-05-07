package dev.axeldiego.realchatapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationDto {
    private String id;
    private List<String> participantIds;
    private String lastMessage;
    private Instant lastMessageAt;
}

