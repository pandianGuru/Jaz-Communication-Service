package com.jaz.email.controller;

import com.jaz.email.config.MessagingConfig;
import com.jaz.email.dto.TicketRequestDto;
import com.jaz.email.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {

    @Autowired
    EmailService emailService;

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void consumeEmailMessageFromQueue(TicketRequestDto ticketRequestDto) {
        log.info("Message received from queue trigger email : " + ticketRequestDto);
        emailService.ticketConfirmationMail(ticketRequestDto);
    }
}
