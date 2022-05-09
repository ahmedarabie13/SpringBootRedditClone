package com.arabie.redditclone.proxy;

import com.arabie.redditclone.domain.dtos.messaging.NotificationEmailDto;
import com.arabie.redditclone.domain.mappers.NotificationEmailMapper;
import com.arabie.redditclone.domain.services.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailRequestConsumer {
    @Value("${rabbitmq.config.queueName}")
    private String queueName;
    private final MailService mailService;
    private final NotificationEmailMapper mapper;

    @RabbitListener(queues = "${rabbitmq.config.queueName}")
    public void getEmailDetails(NotificationEmailDto emailDto) {
        log.info("Email Consumed Successfully");
        mailService.sendEmail(mapper.toEntity(emailDto));
    }
}
