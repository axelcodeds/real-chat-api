package dev.axeldiego.realchatapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMessageRequest {
    private String senderId;
    private String recipientId; // single recipient for simplicity
    private String content;
}

