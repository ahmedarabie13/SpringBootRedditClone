package com.arabie.redditclone.domain.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;
}
