package dev.axeldiego.realchatapi.dto.ws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageWsDto {
    private String senderId;
    private String recipientId;
    private String content;
}
