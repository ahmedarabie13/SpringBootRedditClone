package com.arabie.redditclone.domain.dtos.messaging;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NotificationEmailDto {
    private String subject;
    private String recipient;
    private String body;
}
