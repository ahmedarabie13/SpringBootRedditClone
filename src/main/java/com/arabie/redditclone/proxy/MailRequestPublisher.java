package com.arabie.redditclone.proxy;

import com.arabie.redditclone.domain.mappers.NotificationEmailMapper;
import com.arabie.redditclone.domain.models.NotificationEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor
@Slf4j
public class MailRequestPublisher {
    private final AmqpTemplate template;
    private final NotificationEmailMapper mapper;

    @Value("${rabbitmq.config.exchange}")
    private String exchange;
    @Value("${rabbitmq.config.routingKey}")
    private String routingKey;

    public MailRequestPublisher(@Qualifier("MyRabbit") AmqpTemplate template, NotificationEmailMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }

    public void publishEmail(NotificationEmail email){
        template.convertAndSend(exchange,routingKey,mapper.toDto(email));
        log.info("Email Published Successfully");
    }
}
