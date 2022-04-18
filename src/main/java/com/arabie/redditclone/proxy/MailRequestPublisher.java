package com.arabie.redditclone.proxy;

import com.arabie.redditclone.domain.mappers.NotificationEmailMapper;
import com.arabie.redditclone.domain.models.NotificationEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailRequestPublisher {
    private final RabbitTemplate template;
    private final NotificationEmailMapper mapper;

    @Value("${rabbitmq.config.exchange}")
    private String exchange;
    @Value("${rabbitmq.config.routingKey}")
    private String routingKey;

    public void publishEmail(NotificationEmail email){
        template.convertAndSend(exchange,routingKey,mapper.toDto(email));
        log.info("Email Published Successfully");
    }
}
